package com.fiap.restaurante.infrastructure.adapter.in.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fiap.restaurante.infrastructure.exception.CategoriaInvalidaException;

@RestControllerAdvice
public class ProdutoExceptionHandler {
    @ExceptionHandler(CategoriaInvalidaException.class)
    public ResponseEntity<String> handleInvalidCategoryException(CategoriaInvalidaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
