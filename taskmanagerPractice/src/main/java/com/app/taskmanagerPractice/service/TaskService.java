package com.app.taskmanagerPractice.service;

import com.app.taskmanagerPractice.dto.TaskCreateDTO;
import com.app.taskmanagerPractice.dto.TaskResponseDTO;
import com.app.taskmanagerPractice.exception.AccessDeniedException;
import com.app.taskmanagerPractice.exception.NotFoundException;
import com.app.taskmanagerPractice.model.Role;
import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.repository.TaskRepository;
import com.app.taskmanagerPractice.repository.UserRepository;
import com.app.taskmanagerPractice.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    //Variables de los repositorios que haremos uso
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    // @Autowired aplica el principio D de SOLID. Se crea una interface, esta elige la clase correcta, y se evita añadir un condicional para hacerlo
    // Contructor
    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, SecurityUtils securityUtils) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    // Función para obtener todas las tareas en una lista en formato DTO
    // Se autoriza solo a los miembros con el rol admin
    @Transactional(readOnly = true) // <--- Mantiene la sesión abierta para leer las tareas con seguridad
    public List<TaskResponseDTO> getAllTasks() {
        // Obtenemos tu user
        User currentUser = securityUtils.getCurrentUser();

        //Comprobamos si tu user es admin o no
        boolean isAdmin = currentUser.getRole() == Role.ROLE_ADMIN;
        //Creamos una lista de tareas
        List<Task> tasks;

        // Si es admin cogemos todas las tareas existentes. Si no devuelve tus propias tareas.
        if(isAdmin){
            tasks = taskRepository.findAll();
        } else {
            tasks = currentUser.getTasks();
        }
        //Devolvemos todas las tareas y con el stream hacemos el map para transformar
        //todas las tareas en formato taskResponse y las pasamos a lista
        return tasks.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Método para crear tarea asignando usuario
    public TaskResponseDTO createTask(Long userId, TaskCreateDTO dto) {
        // Comprobamos si existe el usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        //Creamos la tarea, le asignamos los valores
        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(false);
        task.setUser(user);

        //Guardamos la tarea y la pasamos para qué la devuelve en formato dto.
        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    // Añade este método sobrecargado para crear tarea SIN usuario (usado por TaskController)
    public TaskResponseDTO createTask(TaskCreateDTO dto) {
        //Creamos la tarea sin argumentos, le asignamos estos
        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(false);

        //Guardamos la tarea y la pasamos para que la devuelve en formato dto
        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    // Método para obtener una tarea por el id
    public TaskResponseDTO getTaskById(Long id) {
        // Buscamos la tarea por el ID
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));

        //Si obtenemos la tarea la devolvemos en formato dto.
        return toResponseDTO(task);
    }

    //Método para completar una tarea
    public TaskResponseDTO completeTask(Long id) {
        // Buscamos la tarea por el ID
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        //Completamos la tarea
        task.setCompleted(true);
        //Guardamos la tarea y la pasamos para qué la devuelve en formato dto.
        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    // Método para eliminar una tarea por ID
    public void deleteTask(Long id) {
        // 1. Buscamos la tarea (o lanzamos 404 si no existe)
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));

        // 2. Obtenemos el usuario autenticado actual
        User currentUser = securityUtils.getCurrentUser();

        // 3. Verificamos si es ADMIN o si es el dueño de la tarea
        boolean isAdmin = currentUser.getRole() == Role.ROLE_ADMIN;
        boolean isOwner = task.getUser().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("No tienes permisos para eliminar esta tarea");
            // Spring Security interceptará esta excepción y devolverá un 403 Forbidden automáticamente
        }

        // 4. Si pasa la validación, borramos
        taskRepository.delete(task);
    }

    // Método para obtener todas las tareas por ID
    public List<TaskResponseDTO> getPendingTasks() {
        //Devolvemos la tarea donde cogemos todas las tareas y con el stream
        //hacemos el map para transformar todas las tareas de la lista en taskresponseDTO
        //y lo pasamos a lista
        return taskRepository.findByCompletedFalse().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Método para transformar un task en formato TaskResponseDTO
    TaskResponseDTO toResponseDTO(Task task) {
        //Formateador donde de una tarea la pasamos a un formato para devolver una respuesta al cliente
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.isCompleted(),
                task.getUser() != null ? task.getUser().getId() : null
        );
    }

    //Método para obtener las tareas pendientes de forma ordenada.
    public List<TaskResponseDTO> getPendingTasksOrdered() {
        //Devolvemos la tarea donde cogemos todas las tareas y con el stream
        //hacemos el map para transformar todas las tareas de la lista en taskresponseDTO
        //y lo pasamos a lista
        return taskRepository.findPendingTasksOrderedByTitle().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Pageable (El receptor de la petición): Es un objeto que le dice a Spring qué trozo de datos quieres pedir.
    // Page<TaskResponseDTO> Es un objeto envoltorio que devuelve la lista de tareas de esa página más metadatos muy útiles para el frontend, tales como:
    // El número total de páginas que existen, el total de elementos en la base de datos o Si hay una página siguiente o anterior.
    public Page<TaskResponseDTO> getMyTasks(Pageable pageable) {
        //Obtenemos que user esta logeado
        User currentUser = securityUtils.getCurrentUser();
        // Obtenemos las tareas del user por el id y las mapeamos en formato TaskResponseDTO
        return taskRepository.findByUserId(currentUser.getId(), pageable)
                .map(this::toResponseDTO);
    }

}
