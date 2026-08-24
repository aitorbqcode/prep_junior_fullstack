package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.exception.NotFoundException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    // Creamos un repositorio fuera de la base de datos para testear
    @Mock
    private TaskRepository taskRepository;

    // Spring inyecta el mock aquí en el servicio real el mock de arriba
    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("getAllTasks retorna lista vacía cuando no hay tareas")
    void getAllTasks_whenNoTasks_returnsEmptyList() {
        // Arrange: Definimos lo que devolveremos
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        // Act: ejecuta el método que quieres testear
        List<TaskResponseDTO> result = taskService.getAllTasks();

        // Assert: verifica el resultado, comprueba que se haya ejecutado una vez el método findAll
        // del taskRepository
        assertThat(result).isEmpty();
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllTasks debe devolver las tareas que existen")
    void getAllTasks_whenThereAreTask_returnsTheExistingTasks(){
        //Arrange: Definimos lo que devolvemos
        Task task1 = new Task("Aprender Spring", false);
        Task task2 = new Task("Aprender Mockito Testing", false);
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        // Act: ejecuta el método que quieres testear
        List<TaskResponseDTO> result = taskService.getAllTasks();

        // Assert: Verifica que tiene un tamaño 2
        // Que el título de la primera tarea es aprender Spring
        // Verificamos que se ha ejecutado solo una vez
        assertThat(result).hasSize(2);
        assertThat(result.get(0).title()).isEqualTo("Aprender Spring");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getTaskById debe devolver la tarea en concreto por el id buscado")
    void getTaskById_WhenIdExists_ShouldReturnTask() {
        // Arrange
        Task task = new Task("Conectar base de datos", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //Act
        TaskResponseDTO result = taskService.getTaskById(1L);

        //Assert: Comprobamos que no sea null, que coincida el título de la tarea
        // y que se haya ejecutado solo una vez
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Conectar base de datos");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando el ID no existe")
    void getTaskById_WhenIdDoesNotExist_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert: Comprobamos que al buscar esa tarea salte una excepcion
        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(NotFoundException.class);

        //Comprobamos que se haya realizado solo una vez
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar y devolver la tarea creada")
    void createTask_ShouldSaveAndReturnTask() {
        // Arrange: El cliente envía un TaskCreateDTO y el Repository guarda/devuelve una entidad Task
        TaskCreateDTO inputDto = new TaskCreateDTO("Nueva Tarea");
        Task savedTask = new Task("Nueva Tarea", false);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act: Llamamos al servicio con el DTO de creación
        TaskResponseDTO result = taskService.createTask(inputDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Nueva Tarea");
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}