package com.tuapp.taskmanager.config;

import com.tuapp.taskmanager.filter.JwtAuthFilter;
import com.tuapp.taskmanager.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF porque las API stateless basadas en tokens no usan cookies para autenticación
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Le indica a Spring Boot que no cree sesiones HTTP en memoria
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()   // Permite el acceso público sin token a los endpoints de login y registro.
                        .anyRequest().authenticated()               // Cualquier otra URL de la aplicación exigirá haber pasado la autenticación con éxito.
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Coloca tu JwtAuthFilter justo antes del filtro de autenticación por defecto de Spring Security,
                // para que tu validación de JWT se ejecute en primer lugar.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Para que el filtro funcione sin errores en el JwtAuthFilter, diciendole a Spring como buscar a un usuario
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}