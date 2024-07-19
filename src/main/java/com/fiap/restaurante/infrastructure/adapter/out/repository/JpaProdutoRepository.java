package com.fiap.restaurante.infrastructure.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;

public interface JpaProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    
}
