package com.app.taskmanagerPractice;

import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.repository.UserRepository;
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
@AutoConfigureMockMvc(addFilters = false) // Desactiva Spring Security para el test
@ActiveProfiles("test")
@Transactional
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createTask_withValidData_returns201() throws Exception {
        // 1. Guardar usuario usando constructor limpio o setters
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setPassword("password123"); // Opcional para el test
        user = userRepository.save(user);

        // 2. Construir el JSON correspondiente a tu TaskCreateDTO
        String taskJson = """
        {
            "title": "Mi primera tarea",
            "userId": %d
        }
        """.formatted(user.getId());

        // 3. Hacer POST a /api/tasks
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mi primera tarea"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createTask_withEmptyTitle_returns400() throws Exception {
        // Enviar título vacío para disparar el @Valid de TaskCreateDTO
        String invalidTaskJson = """
            {
                "title": "",
                "userId": 1
            }
            """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTaskJson))
                .andExpect(status().isBadRequest());
    }

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
                .andExpect(jsonPath("$.email").value("nuevo@test.com"))
                .andExpect(jsonPath("$.name").value("Usuario Test"));
    }
}