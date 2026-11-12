package com.app.taskmanagerPractice.dto;

//Como este DTO nos sirve para crear un filtro de una entidad para la hora de mostrar la tarea.
// en este caso, que solo se muestren los datos y no la estructura interna.
//Definimos el JSON que enviamos al cliente tras una consulta o en la creacion de una tarea.
//Además, remplaza el objeto user que devuelve y solo devuelve los campos especificados.
public record TaskResponseDTO(
        Long id,
        String title,
        boolean completed,
        Long userId  // solo el id del usuario, no el objeto entero
) {}
