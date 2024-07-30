package com.fiap.restaurante.application.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.application.port.out.ProdutoAdapterPortOut;
import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.core.domain.Produto;

public class ProdutoService implements ProdutoServicePortOut{

    private final ProdutoAdapterPortOut produtoAdapterPortOut;

    public ProdutoService( ProdutoAdapterPortOut produtoAdapterPortOut) {
        this.produtoAdapterPortOut = produtoAdapterPortOut;
    }

    @Override
    public Produto atualizarProduto(Integer id, Produto produto) throws BadRequestException {
        return produtoAdapterPortOut.atualizarProduto(id, produto);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoAdapterPortOut.criarProduto(produto);
    }

    @Override
    public Produto listarProdutoPorId(Integer id) {
        return produtoAdapterPortOut.listarProdutoPorId(id);
    }

    @Override
    public Produto listarProdutoPorCategoria(String categoria) {
        return produtoAdapterPortOut.listarProdutoPorCategoria(categoria);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoAdapterPortOut.listarProdutos();
    }

    @Override
    public void deletarPorId(Integer id) {
        produtoAdapterPortOut.deletarPorId(id);
    }
}
