package com.tuapp.taskmanager.model;

import com.tuapp.taskmanager.exception.ValidationException;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;  // aquí va el hash BCrypt, nunca el texto plano

    // Campo de Rol persistido como String
    @Enumerated(EnumType.STRING) //Nos asegura que en la base de datos el valor se guarde como string en vez de como numeros
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /* Constructor sin argumentos para JPA */
    public User() {}

    /* Constructor personalizado */
    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        this.role = Role.ROLE_USER; // Rol por defecto
    }

    /* Constructor sobrecargado con Rol opcional */
    public User(String name, String email, String password, Role role) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    /* Setters */

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name can't be empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) { // Corrección del bug (comprobaba name)
            throw new ValidationException("Email can't be empty");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password can't be empty");
        }
        this.password = password;
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new ValidationException("Role can't be null");
        }
        this.role = role;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /* Getters */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() { return role; }

    public List<Task> getTasks() {
        return tasks;
    }

    /* Helper Methods */

    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }

    /* Spring Security - UserDetails */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte el Enum Role a SimpleGrantedAuthority
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}