package com.app.taskmanagerPractice.security;

import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.repository.UserRepository;
import com.app.taskmanagerPractice.exception.NotFoundException; // O tu excepción personalizada
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    // Obtenemos tu user
    public User getCurrentUser() {
        // Obtenemos el email
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        //Comprobamos si existe el user y lo devolvemos.
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
