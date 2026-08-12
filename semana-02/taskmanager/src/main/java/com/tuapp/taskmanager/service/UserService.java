package com.tuapp.taskmanager.service;

import com.tuapp.taskmanager.exception.NotFoundException;
import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.repository.TaskRepository;
import com.tuapp.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    /* Constructor */
    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    /* List all users */
    public List<User> getAllUsers() { return userRepository.findAll(); }

    /* Create a user */
    public User createUser(User user) { return userRepository.save(user); }

    /* Get a user by id or thown an exception */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    /* Create a task for user */
    public Task createTaskForUser(Long id, Task task){
        User user = getUserById(id);
        user.addTask(task);
        return taskRepository.save(task);
    }

    /* Get the task of the user */
    public List<Task> getTasksByUserId(Long id){
        User user = getUserById(id);
        return user.getTasks();
    }

    /* Delete the specific user */
    public void deleteUser(Long id) { userRepository.deleteById(id); }

}
