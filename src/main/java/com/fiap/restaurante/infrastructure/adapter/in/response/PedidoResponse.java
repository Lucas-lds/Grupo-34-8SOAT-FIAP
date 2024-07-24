package com.fiap.restaurante.infrastructure.adapter.in.response;

import java.util.List;

import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;

public record PedidoResponse(Integer id, String status, Integer idCliente, List<ProdutoRequest> ListaProduto) {
}