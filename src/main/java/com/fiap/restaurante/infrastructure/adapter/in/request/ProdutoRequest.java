package com.fiap.restaurante.infrastructure.adapter.in.request;

public record ProdutoRequest(Long id, String nome, String categoria, Double preco, String descricao) {

}
