package com.cristobalrivas.playerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    ResponseEntity<Void> handleAccountNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RestClientException.class)
    ResponseEntity<Void> handleRiotFailure() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
}
