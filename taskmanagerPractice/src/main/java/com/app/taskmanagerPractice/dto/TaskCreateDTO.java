package com.app.taskmanagerPractice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Con esta clase nos sirve para crear un filtro de entrada de los datos. En este caso se controlan la entrada de estos
//y que se cumplan unas condiciones
// @NotBlank actua filtros ante la petición Http
//Por un lado con este create controlamos los datos que puede enviar el usuario, en este caso que no este vacio el titulo.
public record TaskCreateDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede superar 100 caracteres")
        String title
) {}
