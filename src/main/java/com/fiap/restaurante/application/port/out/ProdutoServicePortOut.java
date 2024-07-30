package com.fiap.restaurante.application.port.out;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoServicePortOut {
    List<Produto> listarProdutos();

    Produto listarProdutoPorCategoria(String categoria);

    Produto listarProdutoPorId(Integer id);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Integer id, Produto produto) throws BadRequestException;

    void deletarPorId(Integer id);
}
