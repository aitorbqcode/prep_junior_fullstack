package com.app.taskmanagerPractice.dto;

//Como este DTO nos sirve para crear un filtro de una entidad para la hora de realizar una autenticacion
// que solo se muestren los datos y no la estructura interna.
public record AuthResponseDTO(String token) {}
