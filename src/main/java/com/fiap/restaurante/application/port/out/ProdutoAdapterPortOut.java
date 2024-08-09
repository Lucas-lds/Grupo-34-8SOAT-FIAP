package com.fiap.restaurante.application.port.out;

import java.util.List;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoAdapterPortOut {

    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto);

    void deletarPorId(Long id);
}
