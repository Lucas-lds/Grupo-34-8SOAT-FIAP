package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.restaurante.application.port.out.ProdutoAdapterPortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.in.validation.ProdutoValidation;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ProdutoRepository;

@Component
public class ProdutoAdapterOut implements ProdutoAdapterPortOut {

    private final ProdutoRepository produtoRepository;

    public ProdutoAdapterOut( ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto criarProduto(Produto produto) {
        ProdutoValidation.validate(produto.getCategoria());
        return produtoRepository.save(ProdutoEntity.fromDomain(produto)).toDomain();
    }

    @Override
    public Produto listarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow().toDomain();
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        ProdutoValidation.validate(categoria);
        List<ProdutoEntity> produtoEntities = produtoRepository.findProductByCategoria(categoria);
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> listarProdutos() {
        List<ProdutoEntity> produtoEntities = produtoRepository.findAll();
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) {
        Optional<ProdutoEntity> produtoEntityOptional = produtoRepository.findById(id);
        if (produtoEntityOptional.isPresent()) {
            ProdutoEntity produtoEntityExistente = ProdutoEntity.fromDomain(produto);
            boolean isUpdated = false;

            // Atualizar somente os campos modificados
            if (produto.getNome() != null) {
                produtoEntityExistente.setNome(produto.getNome());
                isUpdated = true;
            }
            if (produto.getCategoria() != null) {
                ProdutoValidation.validate(produto.getCategoria());
                produtoEntityExistente.setCategoria(produto.getCategoria());
                isUpdated = true;
            }
            if (produto.getPreco() >= 0) {
                produtoEntityExistente.setPreco(produto.getPreco());
                isUpdated = true;
            }
            if (produto.getDescricao() != null) {
                produtoEntityExistente.setDescricao(produto.getDescricao());
                isUpdated = true;
            }
            if (produto.getImagemUrl() != null) {
                produtoEntityExistente.setImagemUrl(produto.getImagemUrl());
                isUpdated = true;
            }

            if (isUpdated) {
                // Salvar somente se houver mudanças
                ProdutoEntity salvo = produtoRepository.save(produtoEntityExistente);
                return salvo.toDomain();
            } else {
                // Retornar a entidade existente se nada foi atualizado
                return produtoEntityExistente.toDomain();
            }
        } else {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado");
        }
    }

    @Override
    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }
    
}
