package com.example.fullstackbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoProjectFoundByIdException.class)
    public ResponseEntity<String> handleProjectNotFoundException(NoProjectFoundByIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoEmployeeFoundByIdException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(NoEmployeeFoundByIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
