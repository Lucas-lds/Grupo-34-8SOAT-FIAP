package com.fiap.restaurante.application.service;

import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ClienteServicePortOut {

    private final ClienteAdapterPortOut clienteAdapterPortOut;
    private final CognitoAdapterPortOut cognitoAdapterPortOut;
    private final ValidarCpfLambdaPortOut validarCpfLambdaPortOut;

    public ClienteService(ClienteAdapterPortOut clienteAdapterPortOut, CognitoAdapterPortOut cognitoAdapterPortOut,
                          ValidarCpfLambdaPortOut validarCpfLambdaPortOut) {
        this.clienteAdapterPortOut = clienteAdapterPortOut;
        this.cognitoAdapterPortOut = cognitoAdapterPortOut;
        this.validarCpfLambdaPortOut = validarCpfLambdaPortOut;
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        cognitoAdapterPortOut.cadastrarClienteCognito(new ClienteCognitoRequestDTO(cliente.getCpf(), cliente.getNome(),
                cliente.getEmail()));

        return clienteAdapterPortOut.cadastrar(cliente);
    }

    @Override
    public Cliente buscar(String cpf) {

        return clienteAdapterPortOut.buscar(cpf);
    }

    @Override
    public void autenticarCliente(String cpf) {
        validarCpfLambdaPortOut.validarCpf(cpf);
    }

}
