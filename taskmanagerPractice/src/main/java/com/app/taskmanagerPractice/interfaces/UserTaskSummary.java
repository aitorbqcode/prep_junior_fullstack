package com.app.taskmanagerPractice.interfaces;

//Esta interface representa la view UserTaskSummary creada en PostgreeSQL
public interface UserTaskSummary {
    Long getUserId();
    String getName();
    String getEmail();
    Long getTotalTasks();
    Long getCompletedTasks();
    Long getPendingTasks();
    Double getCompletionRate();
}
