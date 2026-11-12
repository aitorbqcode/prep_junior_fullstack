package com.app.taskmanagerPractice.repository;

import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;


// Al poner que viene de JpaRepository, ya tienes: findAll(), findById(), save(), deleteById()
// Spring genera la implementación automáticamente
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Query por nombre de método (Spring lo traduce automáticamente):
    List<Task> findByCompletedFalse();

    // Query manual con JPQL cuando necesitas algo más complejo, como es en este caso, buscar las tareas pendiénte y ordenadas
    // por el título
    @Query("SELECT t FROM Task t WHERE t.completed = false ORDER BY t.title")
    List<Task> findPendingTasksOrderedByTitle();

    // Spring Data JPA lee "findBy" + "User" + "Id" y genera la consulta SQL automáticamente,
    // aceptando además el parámetro 'Pageable' al final para paginar los resultados.
    Page<Task> findByUserId(Long userId, Pageable pageable);
}
