package com.tuapp.taskmanager.repository;

import com.tuapp.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTaskRepository implements TaskRepository{
    private List<Task> tasks = new ArrayList<>();

    /* Return all the list of the repository */
    @Override
    public List<Task> findAll() {
        return tasks;
    }

    /* Add a new task to the repository */
    @Override
    public void save(Task task) {
        tasks.add(task);
    }
}
