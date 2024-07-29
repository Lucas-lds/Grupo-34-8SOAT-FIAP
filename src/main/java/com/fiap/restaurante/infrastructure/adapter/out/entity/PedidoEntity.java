package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.util.List;

import com.fiap.restaurante.core.domain.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pedidos")
public class PedidoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer idCliente;
    
    private List<ProdutoEntity> ListaProdutos;

}
