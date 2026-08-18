package com.tuapp.taskmanager;

import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.repository.UserRepository;
import com.tuapp.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TaskManagerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    // -------------------------------------------------------------
    // 1. Crear un usuario -> espera 201 Created
    // -------------------------------------------------------------
    @Test
    void createUser_withValidData_returns201() throws Exception {
        String newUserJson = """
            {
                "email": "nuevo@test.com",
                "name": "Usuario Test"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("nuevo@test.com"));
    }

    // -------------------------------------------------------------
    // 2. Obtener un usuario que no existe -> espera 404 Not Found
    // -------------------------------------------------------------
    @Test
    void getUser_whenDoesNotExist_returns404() throws Exception {
        mockMvc.perform(get("/api/users/99999"))
                .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------
    // 3. Crear una tarea con título vacío -> espera 400 Bad Request
    // -------------------------------------------------------------
    @Test
    void createTask_withEmptyTitle_returns400() throws Exception {
        // Primero persistimos un usuario real en H2
        User user = userRepository.save(new User("Test User", "user@test.com"));

        String invalidTaskJson = """
            {
                "title": ""
            }
            """;

        mockMvc.perform(post("/api/users/" + user.getId() + "/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTaskJson))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------
    // 4. Completar una tarea -> espera 200 OK y completed: true
    // -------------------------------------------------------------
    @Test
    void completeTask_whenValid_returns200AndCompletedTrue() throws Exception {
        // Opción recomendada usando tus helper methods:
        User user = new User("Test User 2", "user2@test.com");
        Task task = new Task("Tarea pendiente", false);

        user.addTask(task); // Asocia la tarea al usuario y viceversa
        userRepository.save(user); // Guarda ambos si tienes CascadeType.ALL

        // Ejecutamos la petición para marcarla como completada (ejemplo usando PATCH o PUT)
        mockMvc.perform(put("/api/tasks/" + task.getId() + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }
}