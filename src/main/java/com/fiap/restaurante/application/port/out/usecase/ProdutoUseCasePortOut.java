package com.fiap.restaurante.application.port.out.usecase;

import java.util.List;
import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;

public interface ProdutoUseCasePortOut {
    List<ProdutoResponse> listarProdutos();

    ProdutoResponse listarProdutoPorCategoria(String categoria);

    ProdutoResponse listarProdutoPorId(Integer id);

    ProdutoResponse criarProduto(ProdutoRequest produto);

    ProdutoResponse atualizarProduto(Integer id, ProdutoRequest produto) throws BadRequestException;

    void deletarPorId(Integer id);

}
