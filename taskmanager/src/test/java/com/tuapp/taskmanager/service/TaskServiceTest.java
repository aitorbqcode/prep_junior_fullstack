package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.exception.NotFoundTaskException;
import com.tuapp.taskmanager.model.Role;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.repository.TaskRepository;
import com.tuapp.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    // Limpia el contexto de seguridad al finalizar cada test
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // Configura un usuario autenticado en el contexto de seguridad para los tests que lo requieran
    private void mockAuthentication() {
        User mockUser = new User("Test User", "test@email.com", "password", Role.ROLE_USER);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("getAllTasks retorna lista vacía cuando no hay tareas")
    void getAllTasks_whenNoTasks_returnsEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponseDTO> result = taskService.getAllTasks();

        assertThat(result).isEmpty();
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllTasks debe devolver las tareas que existen")
    void getAllTasks_whenThereAreTask_returnsTheExistingTasks() {
        Task task1 = new Task("Aprender Spring", false);
        Task task2 = new Task("Aprender Mockito Testing", false);
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskResponseDTO> result = taskService.getAllTasks();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).title()).isEqualTo("Aprender Spring");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getTaskById debe devolver la tarea en concreto por el id buscado")
    void getTaskById_WhenIdExists_ShouldReturnTask() {
        Task task = new Task("Conectar base de datos", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDTO result = taskService.getTaskById(1L);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Conectar base de datos");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando el ID no existe")
    void getTaskById_WhenIdDoesNotExist_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(NotFoundTaskException.class);

        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar y devolver la tarea creada para el usuario autenticado")
    void createTask_ShouldSaveAndReturnTask() {
        // Arrange
        mockAuthentication(); // Simula el contexto de seguridad con un usuario logueado
        TaskCreateDTO inputDto = new TaskCreateDTO("Nueva Tarea");
        Task savedTask = new Task("Nueva Tarea", false);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        TaskResponseDTO result = taskService.createTask(inputDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Nueva Tarea");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Debe guardar y devolver la tarea creada asociándola a un userId concreto")
    void createTaskWithUserId_ShouldSaveAndReturnTask() {
        // Arrange
        Long userId = 1L;
        User user = new User("Juan", "juan@email.com", "pass", Role.ROLE_USER);
        TaskCreateDTO inputDto = new TaskCreateDTO("Tarea asignada");
        Task savedTask = new Task("Tarea asignada", false);
        savedTask.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        TaskResponseDTO result = taskService.createTask(userId, inputDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Tarea asignada");
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}