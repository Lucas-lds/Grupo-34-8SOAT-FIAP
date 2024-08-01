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
    public Produto atualizarProduto(Integer id, Produto produto) throws BadRequestException {
        return produtoService.atualizarProduto(id, produto);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @Override
    public void deletarPorId(Integer id) {
        produtoService.deletarPorId(id);
    }

    @Override
    public Produto listarProdutoPorId(Integer id) {
        return produtoService.listarProdutoPorId(id);
    }

    @Override
    public Produto listarProdutoPorCategoria(String categoria) {
        return produtoService.listarProdutoPorCategoria(categoria);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos().stream().toList();
    }
}
