package com.fiap.restaurante.infrastructure.exception;

public class ClienteSemPermissaoCognitoException extends RuntimeException {
    public ClienteSemPermissaoCognitoException(String message) {
        super(message);
    }
}