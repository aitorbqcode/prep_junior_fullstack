package com.app.taskmanagerPractice.repository;

import com.app.taskmanagerPractice.model.Task;
import com.app.taskmanagerPractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Esto sirve para indicar a Spring que esta clase se trata del repositorio (BD) encargada de guardar las instancias de la clase User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
// Con el JpaRepository le indicamos sobre que clase vamos a operar y el tipo del identificador.
// En este caso, JpaRepository viene con muchas funcionalidades ya hechas que se suele usar en los repositorios

    // En este caso el getAllUsers ya viene en el JpaRepository, pero como queremos que luego lo ordene por ID añadimos una query en SQL
    @Query("SELECT u FROM User u ORDER BY u.id")
    List<User> getAllUsers();

    // En este caso, estamos relacionando este repositorio con una instancia que no es userm por eso añadimos la query especificando
    // la consulta que se quiere realizar
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findUserTasks(@Param("userId") Long userId);

    // En este caso, estamos relacionando este repositorio con una instancia que no es userm por eso añadimos la query especificando
    // la consulta que se quiere realizar
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.completed = false")
    List<Task> findPendingUserTasks(@Param("userId") Long userId);

    // Como Spring sabe que User tiene un atributo llamado email, con el nombre del atributo que viene con el JpaRepository entiende
    // que queremos buscar un User, a traves del email.
    Optional<User> findByEmail(String email);
}
