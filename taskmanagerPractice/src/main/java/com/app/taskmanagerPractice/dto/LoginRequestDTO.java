package com.app.taskmanagerPractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Con esta clase nos sirve para crear un filtro de entrada de los datos. En este caso se controlan la entrada de estos
//en el login y unas condiciones de entrada que se tienen que controlar
public record LoginRequestDTO(
        @Email String email,
        @NotBlank String password
) {}

