package com.tuapp.taskmanager.repository;

import com.tuapp.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Spring traduce el nombre del método automáticamente
    List<Task> findByCompletedFalse();

    // Query manual con JPQL
    @Query("SELECT t FROM Task t WHERE t.completed = false ORDER BY t.title")
    List<Task> findPendingTasksOrderedByTitle();
}