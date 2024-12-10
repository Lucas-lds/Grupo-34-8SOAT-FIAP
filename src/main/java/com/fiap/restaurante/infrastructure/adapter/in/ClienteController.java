package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.ClienteRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Operações de cliente")
public class ClienteController {

    private final ClienteUseCasePortOut clienteUseCasePortOut;

    public ClienteController(ClienteUseCasePortOut clienteUseCasePortOut) {
        this.clienteUseCasePortOut = clienteUseCasePortOut;
    }

    @Operation(summary = "Cadastrar um novo cliente", description = "Cadastra um cliente no sistema.")
    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {
        var clienteCadastrado = clienteUseCasePortOut.cadastrarCliente(clienteRequest.toDomain());
        var response = ClienteResponse.fromDomain(clienteCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar cliente por CPF", description = "Busca um cliente utilizando o CPF.")
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponse> buscarPorCpf(@PathVariable String cpf) {
        var clienteBuscado = clienteUseCasePortOut.buscarCliente(cpf);
        var response = ClienteResponse.fromDomain(clienteBuscado);
        return ResponseEntity.ok(response);
    }
}
