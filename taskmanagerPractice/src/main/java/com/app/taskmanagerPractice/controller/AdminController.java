package com.app.taskmanagerPractice.controller;

import com.app.taskmanagerPractice.dto.UserResponseDTO;
import com.app.taskmanagerPractice.interfaces.UserTaskSummary;
import com.app.taskmanagerPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Doble seguridad: clase blindada para admins
public class AdminController {

    private final UserService userService; // Inyectamos el servicio que ya tienes

    // Cuando haces una petición GET a /api/users en postman, obtienes una lista en formado UserResponseDTO de todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersForAdmin() {
        // Reutilizamos la lógica que ya tenías en tu UserService
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/stats")
    public List<UserTaskSummary> getStats() {
        return userService.getStats();
    }
}