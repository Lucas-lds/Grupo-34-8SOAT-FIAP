package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.BuscarClienteUseCasePortOut;
import com.fiap.restaurante.application.port.out.usecase.CadastrarClienteUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.ClienteRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final CadastrarClienteUseCasePortOut cadastrarCliente;
    private final BuscarClienteUseCasePortOut buscarCliente;

    public ClienteController(CadastrarClienteUseCasePortOut cadastrarCliente, BuscarClienteUseCasePortOut buscarCliente) {
        this.cadastrarCliente = cadastrarCliente;
        this.buscarCliente = buscarCliente;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {
        var clienteCadastrado = cadastrarCliente.executar(clienteRequest.toDomain());
        var response = ClienteResponse.fromDomain(clienteCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponse> buscarPorCpf(@PathVariable String cpf) {
        var clienteBuscado = buscarCliente.executar(cpf);
        var response = ClienteResponse.fromDomain(clienteBuscado);
        return ResponseEntity.ok(response);
    }
}
