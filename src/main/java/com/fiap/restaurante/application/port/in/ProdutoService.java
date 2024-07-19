package com.fiap.restaurante.application.port.in;

import java.util.List;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoService {
    List<Produto> getAllProdutos();
    Produto createProduto(Produto produto);
    void deleteProduto(Long id);
    Produto updateProduto(Long id, Produto produto);
    List<Produto> getProdutosByCategoria(String categoria);
}
