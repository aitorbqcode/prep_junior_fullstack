/*
package com.app.taskmanagerPractice.repository;


import com.app.taskmanagerPractice.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private List<Task> tasks = new ArrayList<>();

    public InMemoryTaskRepository(List<Task> tasks){
        setTasks(tasks);
    }

    //Setter
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //Getter
    public List<Task> getTasks() {
        return tasks;
    }

    //Methods

    @Override
    public List<Task> findAll() {
        return getTasks();
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }
}

*/
