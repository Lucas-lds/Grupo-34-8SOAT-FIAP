package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.util.Set;

import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedidos")
public class PedidoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status_;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PedidoProdutoEntity> pedidoProdutos;

}
