package com.cristobalrivas.matchservice.error;

public class InvalidRegionException extends RuntimeException {

    public InvalidRegionException(String region) {
        super("La region contiene caracteres no validos: " + region);
    }
}
