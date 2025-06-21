package com.megajuegos.independencia.controller.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleException(CustomRuntimeException ex) {
        log.error(ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getClass().getSimpleName());

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((msg1, msg2) -> msg1 + ". " + msg2)
                .orElse("Validación fallida");

        body.put("message", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
