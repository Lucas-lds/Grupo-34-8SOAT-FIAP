package com.fiap.restaurante.core.usecase;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.domain.Produto;

public class ProdutoUseCase implements ProdutoUseCasePortOut {
    
    private final ProdutoServicePortOut produtoService;

    public ProdutoUseCase( ProdutoServicePortOut produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos().stream().toList();
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        return produtoService.listarProdutoPorCategoria(categoria);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) throws BadRequestException {
        return produtoService.atualizarProduto(id, produto);
    }

    @Override
    public void deletarPorId(Long id) {
        produtoService.deletarPorId(id);
    }
}
