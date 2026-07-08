package TaskManager.Repository;

import Exceptions.SelectException;
import TaskManager.Model.Task;
import Exceptions.AppException;
import TaskManager.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DataBase.DatabaseConnection.getConnection;

public class TaskRepository {

    public void insertTask(Task task){
        String sql = "INSERT INTO task_manager.tasks VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, task.getTaskId());
            ps.setString(2, task.getTaskName());
            ps.setString(3, task.getTaskPriority().toString());
            ps.setBoolean(4, task.isCompleted());
            ps.setString(5, task.getUserId());

            ps.executeUpdate();
            System.out.println("Task created correctly");

        } catch (SQLException e) {
            throw new AppException("Error inserting task: " + e.getMessage());
        }
    }

    public void markAsCompleted(String taskId) {

        String sql = "UPDATE task_manager.tasks SET completed = true WHERE task_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, taskId);
            ps.executeUpdate();
            System.out.println("Task marked as completed");

        } catch (SQLException e) {
            throw new SelectException("Error with UPDATE: " + e.getMessage());
        }
    }

    public List<Task> getUserTasks(String userId) {
        //We write the sql comand
        String sql = "SELECT * FROM task_manager.tasks WHERE tasks.user_id = ?";

        //List of tasks
        List<Task> userTasks = new ArrayList<>();

        //We get a connection and use the prepareStatement to connect with the sql comand
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //We add the user_id for the command
            ps.setString(1, userId);

            //Get the user tasks adding it to the list
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userTasks.add(new Task(
                            rs.getString("task_id"),
                            rs.getString("task_name"),
                            rs.getString("task_priority"),
                            rs.getBoolean("completed"),
                            rs.getString("user_id")
                    ));
                }
            }
            return userTasks;

        } catch (SQLException e) {
            throw new SelectException("Error en SELECT: " + e.getMessage());
        }
    }

    public Task findById(String taskId){
        //We write the sql comand
        String sql = "SELECT * FROM task_manager.tasks WHERE tasks.task_id = ?";

        //We create a task struct to return
        Task task;

        //We get a connection and use the prepareStatement to connect with the sql comand
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //We add the user_id for the command
            ps.setString(1, taskId);

            //We get the result using the result set, which if the a user the if will be true and we will create the user
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                            rs.getString("task_id"),
                            rs.getString("task_name"),
                            rs.getString("task_priority"),
                            rs.getBoolean("completed"),
                            rs.getString("user_id")
                    );
                }
                return null; // Task not found
            }

        } catch (SQLException e) {
            throw new SelectException("Error en SELECT: " + e.getMessage());
        }
    }
}
