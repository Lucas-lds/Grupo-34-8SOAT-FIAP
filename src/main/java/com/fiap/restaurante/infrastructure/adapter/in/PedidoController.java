package com.fiap.restaurante.infrastructure.adapter.in;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.restaurante.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    private final PedidoUseCasePortOut pedidoUseCasePortOut;

    public PedidoController(PedidoUseCasePortOut pedidoUseCasePortOut){
        this.pedidoUseCasePortOut = pedidoUseCasePortOut;
    }

    @PutMapping("/{id}")
    public PedidoResponse atualizarStatusPedido(@PathVariable Long id, @RequestBody StatusRequest statusRequest) throws BadRequestException {
        return pedidoUseCasePortOut.atualizarStatusPedido(statusRequest.status(), id);
    }

    @PostMapping
    public PedidoResponse checkoutPedido(@RequestBody PedidoRequest pedido) {
        return pedidoUseCasePortOut.checkoutPedido(pedido);
    }
    

    @GetMapping("/{id}")
    public PedidoResponse getPedidoById(@PathVariable Long id) {
        return pedidoUseCasePortOut.listarPedidoPorId(id);
    }
    

    @GetMapping
    public List<PedidoResponse> listarPedidos() {
        return pedidoUseCasePortOut.listarPedidos();
    }

}
