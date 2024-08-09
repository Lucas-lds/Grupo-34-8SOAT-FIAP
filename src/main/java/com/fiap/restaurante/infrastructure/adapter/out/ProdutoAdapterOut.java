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
import com.fiap.restaurante.infrastructure.exception.ProdutoException;

@Component
public class ProdutoAdapterOut implements ProdutoAdapterPortOut {

    private final ProdutoRepository produtoRepository;

    public ProdutoAdapterOut( ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> listarProdutos() {
        List<ProdutoEntity> produtoEntities = produtoRepository.findAll();
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
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
    public Produto criarProduto(Produto produto) {
        ProdutoValidation.validateFields(produto);
        return produtoRepository.save(ProdutoEntity.fromDomain(produto)).toDomain();
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) {
        try {
            Optional<ProdutoEntity> produtoEntityOptional = produtoRepository.findById(id);
            if (produtoEntityOptional.isPresent()) {
                ProdutoEntity produtoEntityExistente = produtoEntityOptional.get();
                boolean isUpdated = false;

                // Valida e Atualiza os campos modificados
                if (ProdutoValidation.validateFields(produto)) {
                    produtoEntityExistente.setNome(produto.getNome());
                    produtoEntityExistente.setCategoria(produto.getCategoria());
                    produtoEntityExistente.setPreco(produto.getPreco());
                    produtoEntityExistente.setDescricao(produto.getDescricao());
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
                throw new ProdutoException("Produto com ID " + id + " não encontrado");
            }
        } catch (Exception ex) {
            throw new ProdutoException(ex.getMessage());
        }
    }

    @Override
    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }
    
}
