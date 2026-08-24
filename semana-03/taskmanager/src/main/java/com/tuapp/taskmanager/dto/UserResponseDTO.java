package com.tuapp.taskmanager.dto;

public record UserResponseDTO(
        Long id,
        String email,
        String name
){}