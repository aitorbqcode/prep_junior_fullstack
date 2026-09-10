package com.tuapp.taskmanager.config;

import com.tuapp.taskmanager.model.Role;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Borra el admin previo corrupto para asegurar que la contraseña se encripta de forma limpia
        userRepository.findByEmail("admin@test.com")
                .ifPresent(userRepository::delete);

        User admin = new User("Admin Root", "admin@test.com", passwordEncoder.encode("admin123"));
        admin.setRole(Role.ROLE_ADMIN); // Ajusta según tu Enum (Role.ADMIN o Role.ROLE_ADMIN)

        userRepository.save(admin);
        System.out.println("--> ADMIN CREADO Y CIFRADO CORRECTAMENTE <--");
    }
}