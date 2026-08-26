package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.dto.UserCreateDTO;
import com.tuapp.taskmanager.dto.UserResponseDTO;
import com.tuapp.taskmanager.exception.NotFoundTaskException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.repository.TaskRepository;
import com.tuapp.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tuapp.taskmanager.dto.TaskResponseDTO;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* Listar todos los usuarios */
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    /* Crear usuario desde DTO */
    public UserResponseDTO createUser(UserCreateDTO dto) {

        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto.name(), dto.email(), encodedPassword);
        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    /* Obtener usuario por ID en formato DTO */
    public UserResponseDTO getUserById(Long id) {
        User user = findEntityById(id);
        return mapToResponseDTO(user);
    }

    /* Asignar tarea a un usuario */
    public Task createTaskForUser(Long id, Task task) {
        User user = findEntityById(id);
        user.addTask(task);
        return taskRepository.save(task);
    }

    /* Obtener tareas de un usuario */
    /* Obtener tareas de un usuario mapeadas a DTO */
    public List<TaskResponseDTO> getTasksByUserId(Long id) {
        User user = findEntityById(id);
        return user.getTasks().stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        user.getId()
                ))
                .toList();
    }

    /* Eliminar usuario */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundTaskException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    /* Usuarios sin tareas */
    public List<UserResponseDTO> findUsersWithNoTasks() {
        return userRepository.findUsersWithNoTasks().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // --- Métodos Auxiliares Privados ---

    private User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException("User not found: " + id));
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getName());
    }

    // Método OPTIMIZADO
    public void printUserTasksCountGood() {
        List<User> users = userRepository.findAllWithTasks(); // 1 sola query con LEFT JOIN

        for (User user : users) {
            // Las tareas ya están cargadas en memoria, NO se lanzan más queries
            System.out.println("Usuario " + user.getName() + " tiene " + user.getTasks().size() + " tareas.");
        }
    }
}