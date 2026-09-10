package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TaskResponseDTO> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasks() {
        return ResponseEntity.ok(taskService.getPendingTasks());
    }

    @GetMapping("/pending/ordered")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasksOrdered() {
        return ResponseEntity.ok(taskService.getPendingTasksOrdered());
    }

    @GetMapping("/pending/by-user")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasksOrderedByUser() {
        return ResponseEntity.ok(taskService.getPendingTasksOrderedByUser());
    }
}