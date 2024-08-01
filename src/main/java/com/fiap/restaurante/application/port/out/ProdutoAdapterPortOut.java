package com.fiap.restaurante.application.port.out;

import java.util.List;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoAdapterPortOut {

    List<Produto> listarProdutos();

    Produto listarProdutoPorCategoria(String categoria);

    Produto listarProdutoPorId(Integer id);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Integer id, Produto produto);

    void deletarPorId(Integer id);
}
