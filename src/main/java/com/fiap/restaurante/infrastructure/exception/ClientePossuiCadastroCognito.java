package com.fiap.restaurante.infrastructure.exception;

public class ClientePossuiCadastroCognito extends RuntimeException {
    public ClientePossuiCadastroCognito(String message) {
        super(message);
    }
}