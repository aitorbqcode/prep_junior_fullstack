package com.tuapp.taskmanager.repository;

import com.tuapp.taskmanager.model.Task;
import com.tuapp.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Find users with no tasks query
    @Query("SELECT u FROM User u WHERE u.tasks IS EMPTY")
    List<User> findUsersWithNoTasks();

    // Trae los usuarios Y sus tareas en un único viaje a la BD
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks")
    List<User> findAllWithTasks();

    boolean existsByEmail(String email);
}

