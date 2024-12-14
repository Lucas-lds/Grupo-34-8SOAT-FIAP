package com.fiap.restaurante.infrastructure.adapter.in;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import com.fiap.restaurante.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedido", description = "Operações de pedidos")
public class PedidoController {

    private final PedidoUseCasePortOut pedidoUseCasePortOut;

    public PedidoController(PedidoUseCasePortOut pedidoUseCasePortOut) {
        this.pedidoUseCasePortOut = pedidoUseCasePortOut;
    }

    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido.")
    @PutMapping("/{id}")
    public PedidoResponse atualizarStatusPedido(@PathVariable Long id, @RequestBody StatusRequest statusRequest) throws BadRequestException {
        return pedidoUseCasePortOut.atualizarStatusPedido(statusRequest.status(), id);
    }

    @Operation(summary = "Checkout do pedido", description = "Finaliza o checkout de um pedido.")
    @PostMapping
    public PedidoResponse checkoutPedido(@RequestBody PedidoRequest pedido) {
        return pedidoUseCasePortOut.checkoutPedido(pedido);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido específico pelo seu ID.")
    @GetMapping("/{id}")
    public PedidoResponse getPedidoById(@PathVariable Long id) {
        return pedidoUseCasePortOut.listarPedidoPorId(id);
    }

    @Operation(summary = "Listar todos os pedidos", description = "Lista todos os pedidos registrados no sistema.")
    @GetMapping
    public List<PedidoResponse> listarPedidos() {
        return pedidoUseCasePortOut.listarPedidos();
    }
}