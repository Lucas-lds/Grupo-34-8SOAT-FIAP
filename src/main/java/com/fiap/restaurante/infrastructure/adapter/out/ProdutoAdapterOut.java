package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.ProdutoAdapterPortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ProdutoRepository;

@Component
public class ProdutoAdapterOut implements ProdutoAdapterPortOut {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;

    public ProdutoAdapterOut( ProdutoRepository produtoRepository, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    @Override
    public Produto atualizarProduto(Integer id, Produto produtoDetails) {
        var produto = produtoRepository.findById(id);
        produto.ifPresent(t -> {
            t.setNome(produtoDetails.getNome());
            t.setPreco(produtoDetails.getPreco());
            t.setCategoria(produtoDetails.getCategoria());
            produtoRepository.save(t);
        });
        return mapper.map(produto.get(), Produto.class);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return mapper.map(produtoRepository.save(mapper.map(produto, ProdutoEntity.class)), Produto.class);
    }

    @Override
    public Produto listarProdutoPorId(Integer id) {
        return mapper.map(produtoRepository.findById(id), Produto.class);
    }

    @Override
    public Produto listarProdutoPorCategoria(String categoria) {
        return mapper.map(produtoRepository.findProductByCategoria(categoria), Produto.class);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll().stream()
            .map(produto -> mapper.map(produto, Produto.class)).toList();
    }

    @Override
    public void deletarPorId(Integer id) {
        produtoRepository.deleteById(id);
    }
    
}
