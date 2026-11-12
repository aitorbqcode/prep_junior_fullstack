package com.app.taskmanagerPractice.config;

import com.app.taskmanagerPractice.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean //
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Método que construye toda la cadena de reglas de seguridad usando un estilo fluido (builder)
        // Todo lo que encadenes a continuación formará las reglas de esta cadena.
        http
                // deshabilitado porque usamos JWT, desactiva la proteccion csrf, ya que la seguridad va por tokens en nuestro caso
                .csrf(csrf -> csrf.disable())
                // Define qué rutas son públicas y cuáles están protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()     // Permite que cualquier persona pueda acceder a las rutas de registro y login
                        // En peticiones de tipo GET en esta direccion "/api/admin/**" solo el rol Admin
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority("ROLE_ADMIN")
                        // En peticiones de tipo DELETE en esta direccion "/api/users/**" solo el rol Admin
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()                    // Todo lo demás requiere token
                )
                // Le dice a Spring Security que no cree ni guarde sesiones HTTP en el servidor para los usuarios.
                // Como usas token y no cookies, el servidor olvida el usuario al terminar la petición
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // sin sesión HTTP
                )
                // Conecta tu AuthenticationProvider personalizado con el sistema de seguridad general.
                .authenticationProvider(authenticationProvider)
                // Le decimos que primero compruebe si el usuario trae un token JWT valido antes de pedir iniciar sesion
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // Compila todas estas reglas en un objeto
        return http.build();
    }
}
