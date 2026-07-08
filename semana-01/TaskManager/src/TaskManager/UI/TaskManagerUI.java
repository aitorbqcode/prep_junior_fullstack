package TaskManager.UI;

import Exceptions.AppException;
import TaskManager.Model.Task;
import TaskManager.Model.User;
import TaskManager.Service.TaskService;

import java.util.List;
import java.util.Scanner;

public class TaskManagerUI {

    private TaskService service = new TaskService();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n1. Create user");
            System.out.println("2. Create task");
            System.out.println("3. List user tasks");
            System.out.println("4. Mark task as completed");
            System.out.println("0. Exit");
            System.out.print("Option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createUser();
                case 2 -> createTask();
                case 3 -> userTasks();
                case 4 -> markAsCompleted();
                case 0 -> { return; }
            }
        }
    }

    private void createUser() {
        try {
            System.out.println("\n1. What is your User ID?:");
            String userId = scanner.nextLine();
            System.out.println("2. What is your name?:");
            String userName = scanner.nextLine();
            System.out.println("3. What is your email?:");
            String userEmail = scanner.nextLine();

            service.createUser(new User(userId, userName, userEmail));
            System.out.println("User created successfully");

        } catch (AppException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createTask(){
        try {
            System.out.println("\n1. What is the Task ID? (Must start with TS followed by a 6-digit number):");
            String taskId = scanner.nextLine();
            System.out.println("2. What is the task name?:");
            String taskName = scanner.nextLine();
            System.out.println("3. What is the task priority? (Must be LOW, MEDIUM, or HIGH):");
            String taskPriority = scanner.nextLine();
            System.out.println("4. Which user is it for? (Provide their User ID):");
            String userId = scanner.nextLine();

            service.createTask(new Task(taskId, taskName, taskPriority, false, userId));
            System.out.println("Task created successfully");

        } catch (AppException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void userTasks(){
        try {
            System.out.println("\n1. What is the User ID of the user whose tasks you want to retrieve? (Must start with US followed by a 6-digit number):");
            String userId = scanner.nextLine();

            List<Task> tasks = service.getTasksByUser(userId);

            if (tasks.isEmpty()) {
                System.out.println("No tasks found for this user");
            } else {
                for (Task task : tasks) {
                    System.out.println(task.getTaskId() + " | " + task.getTaskName() + " | " +
                            task.getTaskPriority() + " | " + (task.isCompleted() ? "Completed" : "Pending"));
                }
            }

        } catch (AppException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void markAsCompleted(){
        try {
            System.out.println("\n1. What is the Task ID of the task you want to mark as completed? (Must start with TS followed by a 6-digit number):");
            String taskId = scanner.nextLine();

            service.markTaskAsCompleted(taskId);
            System.out.println("Task marked as completed successfully");

        } catch (AppException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}