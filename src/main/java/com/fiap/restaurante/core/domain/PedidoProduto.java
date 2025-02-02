package com.fiap.restaurante.core.domain;

import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoPedidoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProduto {
    private Produto produto;

    private Integer quantidade;

    public PedidoProdutoRequest toRequest() {
        return new PedidoProdutoRequest(new ProdutoPedidoRequest(produto.getIdProduto()), quantidade);
    }
}
