package com.tuapp.taskmanager.filter;

import com.tuapp.taskmanager.repository.UserRepository;
import com.tuapp.taskmanager.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Garantiza que el codigo se ejecute una vez por cada peticion HTTP, basicamente nos sirve porque las API REST con JWT son STATELESS(es decir no estan con sesion abierta en el servidor)
// por lo que se genera un token, de forma que se comprueba el token antes de llegar al controller, leyendo el token que sea real y que no haya expirado
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
        // Si no hay header o no empieza por "Bearer ", dejamos pasar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);  // quita "Bearer " limpiamos para quedarnos solo con el token

        // Si el token es valida carga el email y el
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);

            // Buscamos el usuario directamente en el repositorio
            UserDetails userDetails = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

            // Registra la autenticación en el contexto de Spring Security
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Permite que la petición continúe su viaje hacia el @RestControlle
        filterChain.doFilter(request, response);
    }

}
