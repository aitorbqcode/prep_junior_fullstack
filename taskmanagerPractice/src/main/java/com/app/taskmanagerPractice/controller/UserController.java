package com.app.taskmanagerPractice.controller;

import com.app.taskmanagerPractice.dto.TaskResponseDTO;
import com.app.taskmanagerPractice.dto.UserCreateDTO;
import com.app.taskmanagerPractice.dto.UserResponseDTO;
import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController indica a Spring que es un controlador
@RestController
// @RequestMapping la direccion que ponemos en postman para llamar a esas funciones
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // @Autowired aplica el principio D de SOLID. Se crea una interface, esta elige la clase correcta, y se evita añadir un condicional para hacerlo
    // Contructor
    @Autowired
    public UserController(UserService userService) { this.userService = userService;}

    // Cuando haces una petición POST a /api/users en postman, añades la estructura requerida en UserCreateDTO en formato JSON
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    // Cuando haces una petición GET a /api/users/{id} en postman, obtienes un user eb formato UserResponseDTO
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){ return ResponseEntity.ok(userService.getUserById(id));}

    // Cuando haces una petición GET a /api/users/{id}/tasks en postman, obtienes una lista de las tareas de un user en formato TaskResponseDTO
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponseDTO>>  getUserTasks(@PathVariable Long id){ return ResponseEntity.ok(userService.findUserTasks(id));}

    // Cuando haces una petición DELETE a /api/users/{id} en postman, elimina el usuario con ese id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Cuando haces una petición GET a /api/users/{id}/pendingTasks en postman, obtienes una lista de las tareas pendientes de un user en formato TaskResponseDTO
    @GetMapping("/{id}/pendingTasks")
    public ResponseEntity<List<TaskResponseDTO>> getPendingUserTasks(@PathVariable Long id) {return ResponseEntity.ok(userService.findPendingUserTasks(id));}
}
