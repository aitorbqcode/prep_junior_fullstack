package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.UserCreateDTO;
import com.tuapp.taskmanager.dto.UserResponseDTO;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/no-tasks")
    public ResponseEntity<List<UserResponseDTO>> getUsersWithNoTasks() {
        return ResponseEntity.ok(userService.findUsersWithNoTasks());
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<Task> createTaskForUser(@PathVariable Long userId, @RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createTaskForUser(userId, task));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getTasksByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}