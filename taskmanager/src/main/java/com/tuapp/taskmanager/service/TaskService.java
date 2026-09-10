package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.exception.NotFoundTaskException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.repository.TaskRepository;
import com.tuapp.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Retorna todas las tareas si es ADMIN, o solo las asignadas si es ROLE_USER
    public List<TaskResponseDTO> getTasksForCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getRole().name().equals("ROLE_ADMIN")) {
            return taskRepository.findAll().stream().map(this::toResponseDTO).toList();
        }

        return taskRepository.findByUserId(currentUser.getId()).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Método para crear tarea asignando un usuario específico mediante su ID (usado por UserController)
    public TaskResponseDTO createTask(Long userId, TaskCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundTaskException("User not found: " + userId));

        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(false);
        task.setUser(user);

        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    // Método para crear tarea asociada al usuario actualmente autenticado
    public TaskResponseDTO createTask(TaskCreateDTO dto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(false);
        task.setUser(currentUser);

        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException("Task not found: " + id));
        return toResponseDTO(task);
    }

    public TaskResponseDTO completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException("Task not found: " + id));
        task.setCompleted(true);
        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundTaskException("Task not found: " + id));

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Si no es ADMIN y la tarea no es suya, rechazamos la petición con 403 Forbidden
        if (!currentUser.getRole().name().equals("ROLE_ADMIN") && !task.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("No tienes permiso para borrar esta tarea");
        }

        taskRepository.delete(task);
    }

    public List<TaskResponseDTO> getPendingTasks() {
        return taskRepository.findByCompletedFalse().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getPendingTasksOrdered() {
        return taskRepository.findPendingTasksOrderedByTitle().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getPendingTasksOrderedByUser() {
        return taskRepository.findPendingTasksOrderedByUserName().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.isCompleted(),
                task.getUser() != null ? task.getUser().getId() : null
        );
    }

    // Método para obtener la totalidad de tareas de la base de datos (usado en tests y vistas de administración)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }
}