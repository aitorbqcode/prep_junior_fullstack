package com.tuapp.taskmanager.repository;

import com.tuapp.taskmanager.model.Task;
import java.util.List;

public interface TaskRepository {
    /* Methods to implements in the task repositories */
    List<Task> findAll();
    void save(Task task);
}