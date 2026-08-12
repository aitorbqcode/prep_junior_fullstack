package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.exception.NotFoundException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    /* Constructor */
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /* Get all the tasks */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
    }

    public Task completeTask(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getPendingTasks() {
        return taskRepository.findByCompletedFalse();
    }

    public List<Task> getPendingTasksOrdered() {
        return taskRepository.findPendingTasksOrderedByTitle();
    }
}