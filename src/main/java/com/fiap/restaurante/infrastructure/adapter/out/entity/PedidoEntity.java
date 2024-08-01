package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.util.Set;

import com.fiap.restaurante.core.domain.OrderStatus;

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

    @JoinColumn(name = "cliente_id")
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @ManyToMany
    @JoinTable(
        name = "tb_pedido_produtos", 
        joinColumns = @JoinColumn(name = "id_produto"), 
        inverseJoinColumns = @JoinColumn(name = "id_pedido")
    )
    @ElementCollection(targetClass = ProdutoEntity.class)
    private Set<ProdutoEntity> ListaProdutos;

}
