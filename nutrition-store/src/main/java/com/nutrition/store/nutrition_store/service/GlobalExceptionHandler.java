package com.nutrition.store.nutrition_store.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nutrition.store.nutrition_store.exception.resourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(resourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(resourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.NOT_FOUND);
    }
}
