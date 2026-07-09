package com.tuapp.taskmanager.model;

import com.tuapp.taskmanager.exception.ValidationException;

public class Task {

    /* Var */
    private Long id;
    private String title;
    private boolean completed;

    /* Constructor */
    public Task(Long id, String title, boolean completed) {
        setId(id);
        setTitle(title);
        setCompleted(completed);
    }

    /* Setters */

    public void setId(Long id) {
        if(id < 0){
            throw new ValidationException("Id should be positive");
        }
        this.id = id;
    }

    public void setTitle(String title) {
        if(title.trim().isEmpty()){
            throw new ValidationException("Title can't be empty");
        }
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /* Getters */

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public boolean isCompleted() { return completed; }
}