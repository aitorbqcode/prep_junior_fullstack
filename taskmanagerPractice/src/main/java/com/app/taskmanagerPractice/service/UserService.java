package com.app.taskmanagerPractice.service;

import com.app.taskmanagerPractice.dto.TaskResponseDTO;
import com.app.taskmanagerPractice.dto.UserCreateDTO;
import com.app.taskmanagerPractice.dto.UserResponseDTO;
import com.app.taskmanagerPractice.exception.NotFoundException;
import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.model.User;
import com.app.taskmanagerPractice.repository.TaskRepository;
import com.app.taskmanagerPractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

// @Service le dice a Spring que esta clase contiene lógica de negocio y debe gestionarla como un Bean.
@Service
public class UserService {
    //Variables
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    // @Autowired aplica el principio D de SOLID. Se crea una interface, esta elige la clase correcta, y se evita añadir un condicional para hacerlo
    // Contructor
    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    // Funciones
    // Obtener todos los usuarios
    public List<UserResponseDTO> getAllUsers(){
        //Obtiene todos los usuarios de la BD y mapea en el formato UserResponseDTO.
        //Finalmente, los coloca en la lista cada uno de ellos
        return userRepository.getAllUsers().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Crear un usuario
    public UserResponseDTO createUser(UserCreateDTO dto){
        // Creamos un usuario a partir de los campos del dto.
        User user = new User(dto.name(), dto.email());
        // Guardamos el usuario en la BD.
        User saved = userRepository.save(user);
        // Devolvemos el user en formato dto.
        return toResponseDTO(saved);

    }

    // Encontrar las tareas de un usuario
    public List<TaskResponseDTO> findUserTasks(Long id){
        // Buscamos el usuario por ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        // Si existe el usuario, convertimos cada tarea en formato TaskResponseDTO y las pasamos a una lista
        return user.getTasks().stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        user.getId()
                ))
                .toList();}

    // Obtener un usuario por ID
    public UserResponseDTO getUserById(Long id){
        // Buscamos el usuario por ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There are not users with this ID"));
        // Si existe lo devolvemos en el formato DTO.
        return toResponseDTO(user);
    }

    // Borrar un usuario por ID.
    public void deleteUserById(Long id){ userRepository.deleteById(id);}

    // Buscar las tareas pendientes de un usuario
    public List<TaskResponseDTO> findPendingUserTasks(Long id){
        // Buscamos el usuario por ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There are not users with this ID"));

        //Obtenemos cada tarea pendiente del usuario y las pasamos a formato TaskResponseDTO. Luego las añadimos en una lista
        return userRepository.findPendingUserTasks(id).stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        user.getId()
                ))
                .toList();
    }

    //Función para pasar un user a formato UserResponseDTO.
    private UserResponseDTO toResponseDTO(User user) {
        //Formateador donde de una tarea la pasamos a un formato para devolver una respuesta al cliente
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getTasks().stream()
                        .map(this::toTaskResponseDTO)
                        .toList()
        );
    }

    // Método para transformar un task en formato TaskResponseDTO
    TaskResponseDTO toTaskResponseDTO(Task task) {
        //Formateador donde de una tarea la pasamos a un formato para devolver una respuesta al cliente
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.isCompleted(),
                task.getUser() != null ? task.getUser().getId() : null
        );
    }
}
