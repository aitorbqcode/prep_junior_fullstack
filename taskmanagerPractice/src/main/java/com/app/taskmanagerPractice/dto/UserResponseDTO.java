package com.app.taskmanagerPractice.dto;

import com.app.taskmanagerPractice.model.Task;

import java.util.List;

//Como este DTO nos sirve para crear un filtro de una entidad para la hora de mostrar el usuario
// en este caso, que solo se muestren los datos y no la estructura interna.
public record UserResponseDTO(
        Long id,
        String email,
        String name,
        List<TaskResponseDTO> tasks
) {}