package com.fiap.restaurante.application.service;

import com.fiap.restaurante.application.port.in.ProdutoService;
import com.fiap.restaurante.application.port.out.ProdutoRepository;
import com.fiap.restaurante.core.domain.Produto;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> getProdutosByCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Override
    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto updateProduto(Long id, Produto produtoDetails) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto not found with id " + id));

        produto.setNome(produtoDetails.getNome());
        produto.setPreco(produtoDetails.getPreco());
        produto.setCategoria(produtoDetails.getCategoria());
        produto.setDescricao(produtoDetails.getDescricao());

        return produtoRepository.save(produto);
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
