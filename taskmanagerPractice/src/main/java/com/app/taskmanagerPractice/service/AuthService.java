package com.app.taskmanagerPractice.service;

import com.app.taskmanagerPractice.dto.AuthResponseDTO;
import com.app.taskmanagerPractice.dto.LoginRequestDTO;
import com.app.taskmanagerPractice.dto.RegisterRequestDTO;
import com.app.taskmanagerPractice.exception.NotFoundException;
import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service le dice a Spring que esta clase contiene lógica de negocio y debe gestionarla como un Bean.
// @RequiredArgsConstructor genera automáticamente un constructor para inyectar los atributos 'final'.
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;         // Para guardar y buscar usuarios en la Base de Datos
    private final PasswordEncoder passwordEncoder;       // Para encriptar las contraseñas con BCrypt
    private final JwtService jwtService;                 // Para generar el token JWT personalizado
    private final AuthenticationManager authenticationManager; // El validador oficial de credenciales de Spring Security

    // Lógica para registrar un nuevo usuario
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Creamos una nueva entidad User y encriptamos su contraseña antes de guardarla
        var user = new User(request.name(), request.email(),
                passwordEncoder.encode(request.password()));
        // Guardamos el usuario en la base de datos
        userRepository.save(user);
        // Generamos un token JWT usando su email como identificador principal y lo devolvemos envuelto en el DTO
        return new AuthResponseDTO(jwtService.generateToken(user));
    }

    // Lógica para iniciar sesión
    public AuthResponseDTO login(LoginRequestDTO request) {
        // Validamos las credenciales mediante el AuthenticationManager.
        // Si el email o la contraseña son incorrectos, Spring Security lanzará automáticamente una excepción de autenticación fallida.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        // Si la autenticación anterior fue exitosa, buscamos al usuario en la base de datos por su email.
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Generamos un token JWT usando su email como identificador principal y lo devolvemos envuelto en el DTO
        return new AuthResponseDTO(jwtService.generateToken(user));
    }
}
