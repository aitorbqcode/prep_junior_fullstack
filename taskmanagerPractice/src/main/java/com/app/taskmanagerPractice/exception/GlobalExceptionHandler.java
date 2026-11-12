package com.app.taskmanagerPractice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //Interceptor global de las excepciones no capturadas de los @RestController
public class GlobalExceptionHandler {

    // Salta cuando se da una tarea que no existe
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        //Creamos el error a partir del response error creado rellenando todos sus parámetros
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), // "Not Found"
                ex.getMessage(),
                request.getRequestURI()
        );
        //Devolvemos el error con el status y el cuerpo del error
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Sirve para cuando se incumplen reglas de negocio(como cuando se deja el titulo de la tarea vacio)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, HttpServletRequest request) {
        //Creamos el error a partir del response error creado rellenando todos sus parametros
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // "Bad Request"
                ex.getMessage(),
                request.getRequestURI()
        );
        //Devolvemos el error con el status y el cuerpo del error
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Para cuando salta cualquier error que no existe
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        //Creamos el error a partir del response error creado rellenando todos sus parametros
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocurrió un error interno en el servidor",
                request.getRequestURI()
        );
        //Devolvemos el error con el status y el cuerpo del error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Salta cuando se produce un error validando los datos Json donde ponemos el @Valid
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        //Creamos un mapa para los errores
        Map<String, String> errors = new HashMap<>();
        // ex es la excepción (MethodArgumentNotValidException) que Spring lanza automáticamente en cuanto el @Valid
        // detecta que un campo de tu DTO no cumple las reglas
        // getBindingResult(): Es el método que te da acceso al resultado de la validación.
        // getFieldErrors(): Del informe completo anterior, este método extrae únicamente los errores específicos de los campos del DTO
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        // Por cada error encontrado, error.getField(): Saca el nombre del campo que falló y
        // error.getDefaultMessage(): Saca el mensaje personalizado que escribiste en el DTO
        return ResponseEntity.badRequest().body(errors);
    }
}
