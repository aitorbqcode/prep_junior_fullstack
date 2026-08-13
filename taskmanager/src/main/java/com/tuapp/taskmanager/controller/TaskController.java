package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.model.Task;
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
    public TaskResponseDTO completeTask(@PathVariable Long id) {
        return taskService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/pending")
    public List<TaskResponseDTO> getPendingTasks() {
        return taskService.getPendingTasks();
    }

    @GetMapping("/pending/ordered")
    public List<TaskResponseDTO> getPendingTasksOrdered() {
        return taskService.getPendingTasksOrdered();
    }

    @GetMapping("/pending/by-user")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasksOrderedByUser() {
        List<TaskResponseDTO> tasks = taskService.getPendingTasksOrderedByUser();
        return ResponseEntity.ok(tasks);
    }
}