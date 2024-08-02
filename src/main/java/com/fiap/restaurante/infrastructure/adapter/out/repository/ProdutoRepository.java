package com.fiap.restaurante.infrastructure.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findProductByCategoria(String categoria);
}
