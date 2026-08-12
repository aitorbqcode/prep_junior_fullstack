package com.tuapp.taskmanager.dto;

// dto/TaskResponseDTO.java — lo que devuelves al cliente
public record TaskResponseDTO(
        Long id,
        String title,
        boolean completed,
        Long userId
) {}

