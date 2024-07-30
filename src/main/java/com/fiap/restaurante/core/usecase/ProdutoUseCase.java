package com.fiap.restaurante.core.usecase;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;

import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;

public class ProdutoUseCase implements ProdutoUseCasePortOut {
    
    private final ProdutoServicePortOut produtoServicePortOut;
    private final ModelMapper mapper;

    public ProdutoUseCase( ProdutoServicePortOut produtoServicePortOut, ModelMapper mapper) {
        this.produtoServicePortOut = produtoServicePortOut;
        this.mapper = mapper;
    }


    @Override
    public ProdutoResponse atualizarProduto(Integer id, ProdutoRequest produto) throws BadRequestException {
        return mapper.map(produtoServicePortOut.atualizarProduto(id, mapper.map(produto, Produto.class)), ProdutoResponse.class);
    }

    @Override
    public ProdutoResponse criarProduto(ProdutoRequest produto) {
        return mapper.map(produtoServicePortOut.criarProduto(mapper.map(produto, Produto.class)), ProdutoResponse.class);
    }

    @Override
    public void deletarPorId(Integer id) {
        produtoServicePortOut.deletarPorId(id);
    }

    @Override
    public ProdutoResponse listarProdutoPorId(Integer id) {
        return mapper.map(produtoServicePortOut.listarProdutoPorId(id), ProdutoResponse.class);
    }

    @Override
    public ProdutoResponse listarProdutoPorCategoria(String categoria) {
        return mapper.map(produtoServicePortOut.listarProdutoPorCategoria(categoria), ProdutoResponse.class);
    }

    @Override
    public List<ProdutoResponse> listarProdutos() {
        return produtoServicePortOut.listarProdutos().stream()
            .map(produto -> mapper.map(produto, ProdutoResponse.class)).toList();
    }
}
