package com.app.taskmanagerPractice.model;

import com.app.taskmanagerPractice.exception.ValidationException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
// Estos elementos viene con el lombok, donde:
// @Data nos genera los getters, setters, toString y algunos métodos más de base. Podemos añadirlos igualmente para añadirles algunas modificaciones
// @NoArgsConstructor te genera un constructor sin argumentos
// @AllArgsConstructor te genera un constructor con todos los argumentos
@Data @NoArgsConstructor @AllArgsConstructor
public class User implements UserDetails {

    //Variables
    //@Id indicamos que esta variable es el identificador
    //@GeneratedValue se usa para indicar que es la clave primaria. Además, aquí la generamos de forma automática al crear un usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Indica que este atributo sea una columna, donde tiene que ser un valor único y no es valido que el valor sea nulo.
    @Column(nullable = false, unique = true)
    private String email;

    //Indica que este atributo sea una columna, donde no es valido que el valor sea nulo.
    @Column(nullable = false)
    private String name;

    // Esto se coloca en la entidad principal, donde está se conecta con otra clase, donde nos indica que un user cualquier número de tareas
    // cascade = CascadeType.ALL nos indica que en este caso que cuando se borra esa instancia de user, se borrarán todas las instancias de las tareas asociadas
    // orphanRemovall = true, indica en este caso, que la tarea no puede quedar sin user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    // Aunque no pongamos column, JPA ya lo hace automaticamente, en los demás casos lo ponemos porque indicamos que sea unico o no nulo
    private String password;  // guardada como hash bcrypt

    //@Enumerated Sirve para indicar que se trata de un enum y que se guarde en la BD como String en vez de un numero
    //@Column(nullable = false) que esta columna no puede tener un valor nulo
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    /* Constructor*/
    public User(String name, String email){
        setName(name);
        setEmail(email);
    }

    // Constructor para el registro con password
    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        this.password = password;
    }

    /* Setters */
    // Con las excepciones aunque se comprueba que no se deje el nombre vacío, en ese caso es para la petición https. Pero
    // cuando se haga de forma interna nos interesa que se controle aquí de dentro por eso las excepciones
    public void setName(String name) {
        if(name.trim().isEmpty()){
            throw new ValidationException("Name can't be empty");
        }
        this.name = name;
    }

    public void setEmail(String email){
        if(email == null || email.trim().isEmpty()){
            throw new ValidationException("Email can't be empty");
        }
        this.email = email;
    }

    /* Getters */
    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }

    // UserDetails necesita esto para que Spring sepa el rol:
    // Nos indica una lista de los roles de los usuarios.
    // GrantedAuthority es una interfaz el cual nos hace crear el SimpleGrantedAuthority para poder pasar el string del enum
    // en un objeto que Spring entienda
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1. role.name() convierte tu Enum (por ejemplo, Role.ADMIN) en un String ("ADMIN")
        // 2. new SimpleGrantedAuthority(...) envuelve ese string en el formato que Spring Security entiende
        // 3. List.of(...) lo empaqueta en una lista inmutable para devolver una colección
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /* Methods */
    //Añadir una tarea a un usuario
    public void addTask(Task task){
        tasks.add(task);
        task.setUser(this);
    }

    // Eliminar una tarea de un usuario
    public void removeTask(Task task){
        tasks.remove(task);
        task.setUser(null);
    }

    // Estado de la cuenta del usuario, dependiendo si tiene acceso o no devuelve un valor u otro
    // De momento siempre está activa.
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
