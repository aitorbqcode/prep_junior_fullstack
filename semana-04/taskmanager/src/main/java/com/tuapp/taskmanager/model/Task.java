package com.tuapp.taskmanager.model;

import com.tuapp.taskmanager.exception.ValidationException;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    /* Var */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    /* Constructor */
    public Task() {}

    public Task(String title, boolean completed) {
        setTitle(title);
        setCompleted(completed);
    }

    /* Setters */

    public void setTitle(String title) {
        if(title.trim().isEmpty()){
            throw new ValidationException("Title can't be empty");
        }
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setUser(User user) { this.user = user; }

    /* Getters */

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public boolean isCompleted() { return completed; }

    public User getUser() { return user; }
}