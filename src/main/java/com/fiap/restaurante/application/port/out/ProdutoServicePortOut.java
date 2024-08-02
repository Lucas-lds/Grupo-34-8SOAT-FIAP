package com.fiap.restaurante.application.port.out;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoServicePortOut {
    List<Produto> listarProdutos();

    Produto listarProdutoPorCategoria(String categoria);

    Produto listarProdutoPorId(Long id);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto) throws BadRequestException;

    void deletarPorId(Long id);
}
