package com.fiap.restaurante.infrastructure.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoPK;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, PedidoProdutoPK>{
    
}
