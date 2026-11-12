package com.app.taskmanagerPractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Con esta clase nos sirve para crear un filtro de entrada de los datos. En este caso se controlan la entrada de estos
//en el registro y unas condiciones de entrada que se tienen que controlar
public record RegisterRequestDTO(
        //@Email sirve para que el elemento enviado en este campo sea formato email
        //@NotBlank este campo no puede estar vacío
        //@Size sirve para indicar el tamaño que debe tener este campo
        @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String name
) {}
