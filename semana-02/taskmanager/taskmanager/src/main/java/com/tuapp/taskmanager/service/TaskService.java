package com.tuapp.taskmanager.service;

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
}