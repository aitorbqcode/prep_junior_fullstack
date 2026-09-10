package com.tuapp.taskmanager.filter;

import com.tuapp.taskmanager.repository.UserRepository;
import com.tuapp.taskmanager.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza por "Bearer ", dejamos pasar la petición
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Quita "Bearer "

        // Intentamos validar el token y autenticar al usuario
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);

            try {
                // Buscamos el usuario en el repositorio
                UserDetails userDetails = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

                // Registra la autenticación en el contexto de Spring Security
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (UsernameNotFoundException e) {
                // Si el usuario guardado en el token ya no existe en la BD (por ejemplo, tras recrearla),
                // limpiamos el contexto para que continúe como petición no autenticada en vez de lanzar un 500
                SecurityContextHolder.clearContext();
            }
        }

        // Permite que la petición continúe su viaje hacia el controller
        filterChain.doFilter(request, response);
    }

    // Omite este filtro por completo para endpoints de autenticación públicos
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
    }
}