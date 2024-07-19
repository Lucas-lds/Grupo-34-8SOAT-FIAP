package com.fiap.restaurante.infrastructure.adapter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fiap.restaurante.application.port.out.ProdutoRepository;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.JpaProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final JpaProdutoRepository jpaProdutoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProdutoRepositoryImpl(JpaProdutoRepository jpaProdutoRepository) {
        this.jpaProdutoRepository = jpaProdutoRepository;
    }

    @Override
    public List<Produto> findAll() {
        return jpaProdutoRepository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Produto> findByCategoria(String categoria) {
        String queryStr = "SELECT p FROM Produtos p WHERE p.categoria = :categoria";
        TypedQuery<ProdutoEntity> query = entityManager.createQuery(queryStr, ProdutoEntity.class);
        query.setParameter("categoria", categoria);
        List<ProdutoEntity> produtoEntities = query.getResultList();
        return produtoEntities.stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return jpaProdutoRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    @Transactional
    public Produto save(Produto produto) {
        ProdutoEntity produtoEntity = mapToEntity(produto);;
        if (produtoEntity.getId() == null) {
            entityManager.persist(produtoEntity);
            return mapToDomain(produtoEntity);
        } else {
            // Realizando um update parcial
            ProdutoEntity existingEntity = entityManager.find(ProdutoEntity.class, produto.getId());
            if (existingEntity != null) {
                if (produto.getNome() != null) {
                    existingEntity.setNome(produto.getNome());
                }
                if (produto.getPreco() != 0) {
                    existingEntity.setPreco(produto.getPreco());
                }
                if (produto.getCategoria() != null) {
                    existingEntity.setCategoria(produto.getCategoria());
                }
                if (produto.getDescricao() != null) {
                    existingEntity.setDescricao(produto.getDescricao());
                }
                ProdutoEntity updatedEntity = entityManager.merge(existingEntity);
                return mapToDomain(updatedEntity);
            }
            else {
                return null;
            }
        }
    }

    public Produto updateProduto(Long id, Produto produtoDetails) {
        ProdutoEntity entity = jpaProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto not found with id " + id));

        entity.setNome(produtoDetails.getNome());
        entity.setPreco(produtoDetails.getPreco());
        entity.setCategoria(produtoDetails.getCategoria());

        entity = jpaProdutoRepository.save(entity);

        return mapToDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        jpaProdutoRepository.deleteById(id);
    }

    private Produto mapToDomain(ProdutoEntity entity) {
        return new Produto(entity.getId(), entity.getNome(), entity.getCategoria(), entity.getPreco(), entity.getDescricao());
    }

    private ProdutoEntity mapToEntity(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setPreco(produto.getPreco());
        produtoEntity.setCategoria(produto.getCategoria());
        return produtoEntity;
    }
}
