package com.tuapp.taskmanager.model;

import com.tuapp.taskmanager.exception.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
/*
 @NoArgsConstructor create a constructor for the cases that they don't send any data
 @AllArgsConstructor creates a constructor for the cases that we get all his variables
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class User {

    /* The id is generated automatically */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* It can't be null, and has to be unique the email */
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /* Constructor*/
    public User(String name, String email){
        setName(name);
        setEmail(email);
    }

    /* Setters */

    public void setName(String name) {
        if(name.trim().isEmpty()){
            throw new ValidationException("Name can't be empty");
        }
        this.name = name;
    }

    public void setEmail(String email){
        if(name.trim().isEmpty()){
            throw new ValidationException("Email can't be empty");
        }
        this.email = email;
    }

    public void setTasks(List<Task> tasks) { this.tasks = tasks;}

    /* Getters */

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public List<Task> getTasks() { return tasks; }

    /* Methods */

    /* Add task to the user list */
    public void addTask(Task task){
        tasks.add(task);
        task.setUser(this);
    }

    /* Remove task of the user list */
    public void removeTask(Task task){
        tasks.remove(task);
        task.setUser(null);
    }
}
