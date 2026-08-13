package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.dto.TaskCreateDTO;
import com.tuapp.taskmanager.dto.TaskResponseDTO;
import com.tuapp.taskmanager.exception.NotFoundException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO createTask(TaskCreateDTO dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(false);

        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        return toResponseDTO(task);
    }

    public TaskResponseDTO completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        task.setCompleted(true);
        Task saved = taskRepository.save(task);
        return toResponseDTO(saved);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> getPendingTasks() {
        return taskRepository.findByCompletedFalse().stream()
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

    public List<TaskResponseDTO> getPendingTasksOrdered() {
        return taskRepository.findPendingTasksOrderedByTitle().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getPendingTasksOrderedByUser() {
        return taskRepository.findPendingTasksOrderedByUserName()
                .stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        task.getUser() != null ? task.getUser().getId() : null
                ))
                .toList();
    }
}