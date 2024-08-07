package com.emerghelp.emerghelp.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerUserNotFound(Exception exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error",exception.getMessage(),"success",false));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error",exception.getMessage(),"success",false));

    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error",exception.getMessage(),"success",false));
    }
}

