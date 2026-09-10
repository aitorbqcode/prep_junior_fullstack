package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.LoginRequestDTO;
import com.tuapp.taskmanager.dto.LoginResponseDTO;
import com.tuapp.taskmanager.dto.UserCreateDTO;
import com.tuapp.taskmanager.dto.UserResponseDTO;
import com.tuapp.taskmanager.exception.DuplicateResourceException;
import com.tuapp.taskmanager.exception.NotFoundUserException;
import com.tuapp.taskmanager.model.Role;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.service.JwtService;
import com.tuapp.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("El email ya está registrado: " + dto.email());
        }

        // Forzamos el rol ADMIN al construir el usuario
        User adminUser = new User(
                dto.name(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                Role.ROLE_ADMIN
        );

        userRepository.save(adminUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Administrador creado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        // 1. Buscar usuario por email → si no existe, lanzar 401
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new NotFoundUserException("Credenciales inválidas"));

        // 2. Verificar contraseña con passwordEncoder.matches()
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new NotFoundUserException("Credenciales inválidas");
        }

        // 3. Generar JWT pasando la entidad 'user' completa (con email y rol)
        String token = jwtService.generateToken(user);

        // 4. Devolver 200 con el token
        return ResponseEntity.ok(new LoginResponseDTO(token, dto.email()));
    }
}
