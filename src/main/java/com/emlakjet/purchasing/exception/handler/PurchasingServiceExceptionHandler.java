package com.emlakjet.purchasing.exception.handler;

import com.emlakjet.purchasing.exception.AuthenticationException;
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
    public ResponseEntity<String> handle(AccessDeniedException ex) {
        LOG.error("Access denied exception!", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You are not authorized to perform this action.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handle(AuthenticationException ex) {
        LOG.error("Authentication exception!", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException ex) {
        LOG.error("Illegal argument exception!", ex);
        return ResponseEntity.badRequest().body("Illegal argument exception! " + ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> conflict(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        LOG.error("Service runtime exception!", ex);
        return ResponseEntity.badRequest().body("Service runtime exception! " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        LOG.error("Internal server error!", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server occurred a error!");
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
