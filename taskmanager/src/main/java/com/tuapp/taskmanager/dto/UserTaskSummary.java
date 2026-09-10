package com.tuapp.taskmanager.dto;

public interface UserTaskSummary {
    Long getUserId();
    String getName();
    String getEmail();
    Long getTotalTasks();
    Long getCompletedTasks();
    Long getPendingTasks();
    Double getCompletionRate();
}