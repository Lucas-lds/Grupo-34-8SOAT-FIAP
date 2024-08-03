package com.fiap.restaurante.infrastructure.adapter.in.handler;

import com.fiap.restaurante.infrastructure.exception.EmailDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClienteExceptionHandler {
    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ErroRequisicaoResponse> handleEmailDuplicadoException(EmailDuplicadoException ex) {
        ErroRequisicaoResponse errorResponse = new ErroRequisicaoResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
