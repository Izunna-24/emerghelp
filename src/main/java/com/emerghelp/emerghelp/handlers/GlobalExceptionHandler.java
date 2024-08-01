package com.emerghelp.emerghelp.handlers;

import com.emerghelp.emerghelp.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handlerUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error",exception.getMessage(),"success",false));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("error",exception.getMessage(),"success",false));
    }
}

