package TaskManager.Model;

import Exceptions.*;
import TaskManager.Enum.Priority;

/**
 * Invariante de representación:
 * - endTime >= CurrentTime
 * - Task_id can't be empty, and has to start with TS
 * - Task_name can't be empty
 * - Task_description can't be emptu
 * - Task_priority has to be LOW, MEDIUM or HIGH
 */

public class Task {

    /* Variables */
    private String taskId;
    private String taskName;
    private Priority taskPriority;
    private boolean completed;
    private String userId;

    /* Constructor */
    public Task(String taskId, String taskName, String taskPriority, boolean completed, String userId){
        setTaskId(taskId);
        setTaskName(taskName);
        setTaskPriority(taskPriority);
        setCompleted(completed);
        setUserId(userId);
    }

    /* Setters */
    public void setTaskId(String taskId) {
        if(taskId.trim().isEmpty() || !taskId.startsWith("TS")){
            throw new ValidationException("Task ID should start with TS");
        }
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        if(taskName.trim().isEmpty()){
            throw new ValidationException("Task name should not be empty");
        }
        this.taskName = taskName;
    }

    public void setTaskPriority(String taskPriority) {
        /* Check the category exist in the enum and it's not null */
        if (taskPriority == null || !Priority.existEnum(taskPriority)){
            throw new ValidationException("The task priority should be an existing one");
        }
        this.taskPriority = Priority.valueOf(taskPriority.toUpperCase());
    }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public void setUserId(String userId) {
        if(userId == null || !userId.startsWith("US")){
            throw new ValidationException("User ID should start with US");
        }
        this.userId = userId;
    }

    /* Setters */

    public String getTaskId() { return taskId; }

    public String getTaskName() { return taskName; }

    public Priority getTaskPriority() { return taskPriority; }

    public boolean isCompleted() { return completed; }

    public String getUserId() { return userId; }
}
