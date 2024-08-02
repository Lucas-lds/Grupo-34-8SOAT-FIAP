package com.fiap.restaurante.infrastructure.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{
    
}
