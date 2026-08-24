package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.UserCreateDTO;
import com.tuapp.taskmanager.dto.UserResponseDTO;
import com.tuapp.taskmanager.exception.DuplicateResourceException;
import com.tuapp.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tuapp.taskmanager.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDTO dto) {

        // 1. Validar duplicado
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("El email ya está registrado: " + dto.email());
        }

        // 2. Crear usuario a través del servicio (cifra contraseña y guarda)
        UserResponseDTO createdUser = userService.createUser(dto);

        // 3. Retornar 201 Created con el usuario creado (sin devolver la password)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
