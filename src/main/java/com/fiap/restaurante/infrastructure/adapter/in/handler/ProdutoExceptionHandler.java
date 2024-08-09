package com.fiap.restaurante.infrastructure.adapter.in.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fiap.restaurante.infrastructure.exception.ProdutoException;

@RestControllerAdvice
public class ProdutoExceptionHandler {
    @ExceptionHandler(ProdutoException.class)
    public ResponseEntity<ErroRequisicaoResponse> handleInvalidCategoryException(ProdutoException ex) {
        ErroRequisicaoResponse errorResponse = new ErroRequisicaoResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }
}
