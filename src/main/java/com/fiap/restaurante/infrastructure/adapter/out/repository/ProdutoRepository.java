package com.fiap.restaurante.infrastructure.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    Optional<ProdutoEntity> findProductByCategoria(String categoria);
}
