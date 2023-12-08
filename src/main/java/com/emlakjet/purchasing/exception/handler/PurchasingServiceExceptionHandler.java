package com.emlakjet.purchasing.exception.handler;

import com.emlakjet.purchasing.exception.AuthenticationException;
import com.emlakjet.purchasing.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PurchasingServiceExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PurchasingServiceExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse<String>> handle(AccessDeniedException ex) {
        LOG.error("Access denied exception!", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(GenericResponse.<String>builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .data(ex.getMessage())
                        .message("Access denied!")
                        .success(false)
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GenericResponse<String>> handle(AuthenticationException ex) {
        LOG.error("Authentication exception!", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(GenericResponse.<String>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .data(ex.getMessage())
                        .message("Authentication failed!")
                        .success(false)
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<String>> handle(IllegalArgumentException ex) {
        LOG.error("Illegal argument exception!", ex);
        return ResponseEntity.badRequest().body(
                GenericResponse.<String>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .data(ex.getMessage())
                        .message("Illegal argument!")
                        .success(false)
                        .build()
        );
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse<String>> conflict(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                GenericResponse.<String>builder()
                        .code(HttpStatus.CONFLICT.value())
                        .data(e.getMessage())
                        .message("Data integrity violation!")
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponse<String>> handle(RuntimeException ex) {
        LOG.error("Service runtime exception!", ex);
        return ResponseEntity.badRequest().body(
                GenericResponse.<String>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .data(ex.getMessage())
                        .message("Service runtime exception!")
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<String>> handle(Exception ex) {
        LOG.error("Internal server error!", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GenericResponse.<String>builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .data(ex.getMessage())
                        .message("Internal server error!")
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException ex) {
        LOG.error("Method argument validation exception!", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
