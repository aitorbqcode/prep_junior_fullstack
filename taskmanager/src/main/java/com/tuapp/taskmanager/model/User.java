package com.tuapp.taskmanager.model;

import com.tuapp.taskmanager.exception.ValidationException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /* Constructor sin argumentos para JPA */
    public User() {}

    /* Constructor personalizado */
    public User(String name, String email) {
        setName(name);
        setEmail(email);
    }

    /* Setters */

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name can't be empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) { // Corrección del bug (comprobaba name)
            throw new ValidationException("Email can't be empty");
        }
        this.email = email;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /* Getters */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /* Helper Methods */

    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }
}