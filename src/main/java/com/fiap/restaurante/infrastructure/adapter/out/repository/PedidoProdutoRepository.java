package com.fiap.restaurante.infrastructure.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, Long>{

    List<PedidoProdutoEntity> findByPedidoId(Long id);

}
