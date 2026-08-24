package com.tuapp.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

// dto/TaskCreateDTO.java — lo que recibes del cliente
public record TaskCreateDTO(
        @NotBlank(message = "El título no puede estar vacío")
        String title
) {}
