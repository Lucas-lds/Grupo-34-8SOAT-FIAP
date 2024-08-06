package com.fiap.restaurante.infrastructure.exception;

public class CategoriaInvalidaException extends RuntimeException {
    
    public CategoriaInvalidaException(String message) {
        super(message);
    }
}
