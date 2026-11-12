package com.app.taskmanagerPractice.config;

import com.app.taskmanagerPractice.exception.NotFoundException;
import com.app.taskmanagerPractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    // Dado un identificador, busca el usuario en la base de datos y lo devuelve a spring security
    public UserDetailsService userDetailsService() {
        //Cuando necesitamos autenticar a alguien, llama internamente a este método pasándole el email
        //Devuelve un objeto de tu clase User. Como tu entidad User implementa la interfaz UserDetails
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
    }

    @Bean
    // Lo usamos para comparar credenciales. Juntamos lo que necesitamos para validar el login
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Le dice que use tu userDetailsService para encontrar al usuario en la base de datos.
        provider.setUserDetailsService(userDetailsService());
        // Le dice que use tu passwordEncoder para comparar la contraseña que escribió el usuario
        // en el login con el hash seguro guardado en la base de datos
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    // Creamos el administrador de autenticación.
    // Recibe la petición de login desde tu AuthService. Cuando intentas iniciar sesión, este manager
    // le pregunta al AuthenticationProvider si el email y la contraseña son correctos
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    // Configura el algoritmo de encriptación de contraseñas, usando BCryp
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}