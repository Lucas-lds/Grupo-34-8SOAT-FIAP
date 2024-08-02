package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.util.Set;

import com.fiap.restaurante.core.domain.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedidos")
public class PedidoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status_;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
    
    @OneToMany(mappedBy = "pedido", orphanRemoval = true)
    private Set<PedidoProdutoEntity> pedidoProdutos;

}
