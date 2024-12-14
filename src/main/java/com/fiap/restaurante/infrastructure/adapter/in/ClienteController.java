package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.ClienteRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteUseCasePortOut clienteUseCasePortOut;

    public ClienteController(ClienteUseCasePortOut clienteUseCasePortOut) {
        this.clienteUseCasePortOut = clienteUseCasePortOut;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {
        var clienteCadastrado = clienteUseCasePortOut.cadastrarCliente(clienteRequest.toDomain());
        var response = ClienteResponse.fromDomain(clienteCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponse> buscarPorCpf(@PathVariable String cpf) {
        var clienteBuscado = clienteUseCasePortOut.buscarCliente(cpf);
        var response = ClienteResponse.fromDomain(clienteBuscado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/{cpf}")
    public ResponseEntity<String> validarUsuario(@PathVariable String cpf) {
        clienteUseCasePortOut.validarAutenticacaoCliente(cpf);
        return ResponseEntity.ok("Usuario autenticado com sucesso!");
    }
}
