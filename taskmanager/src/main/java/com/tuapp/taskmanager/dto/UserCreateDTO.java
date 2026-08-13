package com.tuapp.taskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO (
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,
        @Email(message = "El formato de email no es válido")
        @NotBlank(message = "El email no puede estar vacío")
        String email
){}
