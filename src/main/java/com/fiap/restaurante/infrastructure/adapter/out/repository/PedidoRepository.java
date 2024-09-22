package com.fiap.restaurante.infrastructure.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{

    @Query("SELECT p FROM PedidoEntity p WHERE p.status <> 'FINISHED' " +
            "ORDER BY " +
            "CASE p.status " +
            "WHEN 'DONE' THEN 1 " +
            "WHEN 'PREPARING' THEN 2 " +
            "WHEN 'RECEIVED' THEN 3 " +
            "END, p.createdAt ASC")
    List<PedidoEntity> findAllOrderedByStatus();
    
}
