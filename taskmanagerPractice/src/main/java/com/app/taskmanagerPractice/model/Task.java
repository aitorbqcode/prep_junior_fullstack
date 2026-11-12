package com.app.taskmanagerPractice.model;

import com.app.taskmanagerPractice.exception.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// model/Task.java — POJO simple, sin JPA todavía
@Entity //Convertimos la clase en una entidad JPA
@Table(name = "tasks")
//Esto para crearnos los getters solos y unos setters no finales
@Getter @Setter
//Nos crea un constructor sin argumentos y otro con todos
@NoArgsConstructor @AllArgsConstructor
public class Task {

    //Variables
    //@Id indicamos que esta variable es el identificador
    //@GeneratedValue se usa para indicar que es la clave primaria. Además, aquí la generamos de forma automática al crear un usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Decimos que la columna title no puede ser false
    @Column(nullable = false)
    private String title;

    //Para indicar si está completada o no
    private boolean completed;

    // @JoinColumn Esto nos crea una relación con el user, donde esta columna se relaciona con el userID.
    // @ManyToOne Esto nos indica que muchas tareas pueden formar parte de un usuario.
    // fetch = FetchType.LAZY hace que solo se carguen las tareas del usuario que se usa en ese momento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Constructor
    public Task(String title, boolean completed){
        setTitle(title);
        setCompleted(completed);
    }

    //Setters
    public void setId(Long id) {
        if(id == null){
            throw new ValidationException("Id can't be empty");
        }
        this.id = id;
    }

    public void setTitle(String title) {
        if(title.trim().isEmpty()){
            throw new ValidationException("The title of the task can't be empty");
        }
        this.title = title;
    }
}
