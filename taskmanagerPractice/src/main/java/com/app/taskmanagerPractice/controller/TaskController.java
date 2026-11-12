package com.app.taskmanagerPractice.controller;

import com.app.taskmanagerPractice.dto.TaskCreateDTO;
import com.app.taskmanagerPractice.dto.TaskResponseDTO;
import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController indica a Spring que es un controlador
@RestController
// @RequestMapping la direccion que ponemos en postman para llamar a esas funciones
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    // @Autowired aplica el principio D de SOLID. Se crea una interface, esta elige la clase correcta, y se evita añadir un condicional para hacerlo
    // Contructor
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Cuando haces una petición GET a /api/tasks en postman, obtienes todas las tareas en formato TaskResponseDTO
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Cuando haces una petición POST a /api/tasks en postman, creas un tarea dando el formato TaskCreateDTO para crearla
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto));
    }

    // Cuando haces una petición GET a /api/tasks/{id}/complete en postman, obtienes la tarea con ese id en formato TaskResponseDTO
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Cuando haces una petición PUT a /api/tasks/{id}/complete en postman, pones la tarea con ese id en complete y obtiene un TaskResponseDTO
    @PutMapping("/{id}/complete")
    public ResponseEntity<TaskResponseDTO> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }

    // Cuando haces una petición DELETE a /api/tasks/{id} en postman, elimina la tarea con ese id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

    // Cuando haces una petición GET a /api/tasks/pending en postman, obtienes las tareas pendientes en formato TaskResponseDTO
    @GetMapping("/pending")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasks() {
        return ResponseEntity.ok(taskService.getPendingTasks());
    }

    // Cuando haces una petición GET a /api/tasks/pending en postman, obtienes las tareas pendientes en formato TaskResponseDTO de forma ordenada
    @GetMapping("/pending/ordered")
    public ResponseEntity<List<TaskResponseDTO>> getPendingTasksOrdered() {
        return ResponseEntity.ok(taskService.getPendingTasksOrdered());
    }
}
