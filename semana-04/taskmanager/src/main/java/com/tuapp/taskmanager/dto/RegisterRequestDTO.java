package com.tuapp.taskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String name
) {}
