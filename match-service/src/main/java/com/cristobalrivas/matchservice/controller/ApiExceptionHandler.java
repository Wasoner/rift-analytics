package com.cristobalrivas.matchservice.controller;

import com.cristobalrivas.matchservice.error.InvalidRegionException;
import com.cristobalrivas.matchservice.error.PlayerNotFoundException;
import com.cristobalrivas.matchservice.error.PlayerServiceBadResponseException;
import com.cristobalrivas.matchservice.error.PlayerServiceUnavailableException;
import com.cristobalrivas.matchservice.error.RiotBadResponseException;
import com.cristobalrivas.matchservice.error.RiotUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRegionException.class)
    ResponseEntity<Void> handleInvalidRegion() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    ResponseEntity<Void> handlePlayerNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PlayerServiceUnavailableException.class)
    ResponseEntity<Void> handlePlayerServiceUnavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @ExceptionHandler(PlayerServiceBadResponseException.class)
    ResponseEntity<Void> handlePlayerServiceBadResponse() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    @ExceptionHandler(RiotUnavailableException.class)
    ResponseEntity<Void> handleRiotUnavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @ExceptionHandler(RiotBadResponseException.class)
    ResponseEntity<Void> handleRiotBadResponse() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
}
