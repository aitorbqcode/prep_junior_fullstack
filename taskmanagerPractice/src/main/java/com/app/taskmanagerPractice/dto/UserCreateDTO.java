package com.app.taskmanagerPractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Como esta en record nos sirve para crear un filtro de entrada de los datos, en este caso se controlan la entrada de estos
//y que se cumplan unas condiciones
// Los filtros @NotBlank, @Email actuan de filtros ante la petición Http
public record UserCreateDTO(
        @NotBlank(message = "El nombre no puede estar vacio")
        String name,
        @Email(message = "El formato de email es incorrecto")
        @NotBlank(message = "El email no puede estar vacio")
        String email
){}
