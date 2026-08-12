package com.tuapp.taskmanager.controller;

import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import com.tuapp.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() { return userService.getAllUsers(); }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/{userId}/tasks")
    public Task createTaskForUser(@PathVariable Long userId, @RequestBody Task task) {
        return userService.createTaskForUser(userId, task);
    }

    @GetMapping("/{userId}/tasks")
    public List<Task> getTasksByUserId(@PathVariable Long userId) {
        return userService.getTasksByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
