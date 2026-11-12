package com.app.taskmanagerPractice.controller;

import com.app.taskmanagerPractice.dto.AuthResponseDTO;
import com.app.taskmanagerPractice.dto.LoginRequestDTO;
import com.app.taskmanagerPractice.dto.RegisterRequestDTO;
import com.app.taskmanagerPractice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController indica que esta clase es un controlador REST (responde peticiones HTTP, normalmente en JSON).
// @RequestMapping("/api/auth") define que todas las rutas de este controlador empiezan por /api/auth.
// @RequiredArgsConstructor genera automáticamente un constructor para inyectar los atributos 'final'.
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Inyectamos el servicio de autenticación, que es donde reside la lógica de negocio (crear usuario, validar login, etc.).
    private final AuthService authService;

    //Funciones

    /**
     * Endpoint para registrar un nuevo usuario.
     * Ruta final: POST /api/auth/register
     * @param request Datos del usuario que envía el cliente (nombre, email, contraseña).
     * @return ResponseEntity con código HTTP 200 (OK) y el Token JWT envuelto en un AuthResponseDTO.
     */
    // Función para registrarse un nuevo usuario
    // Cuando haces una petición POST a /api/auth/register en postman, añades la estructura requerida en RegisterRequestDTO en formato JSON
    // @Valid comprueba que se cumplan las validaciones puestas en el RegisterRequestDTO como @NotBlank, @Size, etc
    // @RequestBody Spring coge el Json de postman y lo mapea en una instancia de la clase RegisterRequestDTO.
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        //Entonces con el ResponseEntity te permite controlar la respuesta HTTP que le envías al cliente
        //authService.register a partir de la request generará un token el cual es guardado en AuthResponseDTO(token)
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint para iniciar sesión (login) en la aplicación.
     * Ruta final: POST /api/auth/login
     * @param request Credenciales del usuario (email y contraseña).
     * @return ResponseEntity con código HTTP 200 (OK) si las credenciales son correctas, devolviendo un nuevo Token JWT.
     */
    // Función para iniciar sesion en la aplicacion
    // Cuando haces una petición POST a /api/auth/login en postman, añades la estructura requerida en LoginRequestDTO en formato JSON
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        //Entonces con el ResponseEntity te permite controlar la respuesta HTTP que le envías al cliente
        //authService.login a partir de la request generará un token el cual es guardado en AuthResponseDTO(token)
        return ResponseEntity.ok(authService.login(request));
    }
}