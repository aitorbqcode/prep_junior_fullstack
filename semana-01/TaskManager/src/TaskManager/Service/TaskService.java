package TaskManager.Service;

import TaskManager.Model.Task;
import TaskManager.Model.User;
import TaskManager.Repository.TaskRepository;
import TaskManager.Repository.UserRepository;
import Exceptions.NotFoundException;

import java.util.List;

public class TaskService {

    private final UserRepository userRepository = new UserRepository();
    private final TaskRepository taskRepository = new TaskRepository();

    public void createUser(User user) {
        userRepository.insertUser(user);
    }

    public void createTask(Task task) {
        // Verify the user exists before adding it to the repository
        User user = userRepository.findById(task.getUserId());
        if (user == null) {
            throw new NotFoundException("User not found: " + task.getUserId());
        }
        taskRepository.insertTask(task);
    }

    public List<Task> getTasksByUser(String userId) {
        // Verifica the user exist before getting all his tasks
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found: " + userId);
        }
        return taskRepository.getUserTasks(userId);
    }

    public void markTaskAsCompleted(String taskId) {
        // Verify the task exist before mark it to complete
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        taskRepository.markAsCompleted(taskId);
    }
}