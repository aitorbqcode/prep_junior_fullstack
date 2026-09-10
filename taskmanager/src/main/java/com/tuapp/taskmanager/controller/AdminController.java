package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.UserResponseDTO;
import com.tuapp.taskmanager.dto.UserTaskSummary;
import com.tuapp.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Endpoint seguro para consultar las métricas de la vista
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserTaskSummary>> getStats() {
        return ResponseEntity.ok(userService.getUserStats());
    }
}