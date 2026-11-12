package com.app.taskmanagerPractice.filter;

import com.app.taskmanagerPractice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Sirve para interceptar cualquier petición http antes de que llegue al controlador.
// Comprueba si la petición trae la cabecera Authorization: Bearer <token>.
// Si la trae, le pide al JwtService que valide si ese token es real y de qué usuario es.
// Si es válido, "registra" oficialmente al usuario en Spring Security (SecurityContextHolder)
// para que el resto de la aplicación sepa quién está llamando a la API.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Variables
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,  FilterChain filterChain)
            throws ServletException, IOException {

        //Pedimos obtener la cabecera de la autorizacion
        final String authHeader = request.getHeader("Authorization");

        //Comprobamos que los datos obtenidos no sean null y que empiecen con Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si no se cumple la condición anterior nos quedamos con el texto que viene despues del Bearer Authorization
        final String jwt = authHeader.substring(7);
        //Extraemos el email
        final String email = jwtService.extractEmail(jwt);

        //Si el email no es null. Lo segundo es para comprobar que el usuario ya ha sido autenticado, si no está autenticado se
        // hace la autenticación
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Los detalles del usuario a partir del email
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            //Si el token es valido
            if (jwtService.isTokenValid(jwt, email)) {
                //Con esto no lo entiendo
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,                   // 1. El usuario (Principal)
                                null,                          // 2. Credenciales (Contraseña) -> Se pone null porque ya no hace falta (ya validamos el JWT)
                                userDetails.getAuthorities()   // 3. Permisos o Roles (ej. ROLE_USER, ROLE_ADMIN)
                        );
                //Adjunta información técnica adicional de la petición HTTP al objeto de autenticación.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Lo guardas en el SecurityContextHolder para que el resto de la aplicación (y tus controladores)
                // sepan quién es el usuario que está haciendo la petición.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //Sirve para que pase al siguiente filtro, sinó se detiene en seco y no sabe como continuar.
        filterChain.doFilter(request, response);
    }
}
