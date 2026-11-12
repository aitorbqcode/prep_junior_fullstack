package com.app.taskmanagerPractice.dto;

//Como este DTO nos sirve para crear un filtro de una entidad para la hora de realizar el login
// para decidir que datos queremos dar como respuesta
public record LoginResponseDTO(String token, String email) {}

