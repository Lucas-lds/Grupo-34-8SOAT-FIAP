package com.fiap.restaurante.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;


import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteAdapterPortOut clienteAdapterPortOut;

    @Mock
    private CognitoAdapterPortOut cognitoAdapterPortOut;

    @Mock
    private ValidarCpfLambdaPortOut validarCpfLambdaPortOut;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "123.456.789-00", "João", "joao@example.com", "12345");
    }

    @Test
    void cadastrarDeveCadastrarClienteComSucesso() {
        doNothing().when(cognitoAdapterPortOut).cadastrarClienteCognito(any(ClienteCognitoRequestDTO.class));
        when(clienteAdapterPortOut.cadastrar(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.cadastrar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente.getCpf(), resultado.getCpf());
        verify(cognitoAdapterPortOut, times(1)).cadastrarClienteCognito(any(ClienteCognitoRequestDTO.class));
        verify(clienteAdapterPortOut, times(1)).cadastrar(any(Cliente.class));
    }

    @Test
    void buscarDeveRetornarClienteQuandoEncontrado() {
        when(clienteAdapterPortOut.buscar(anyString())).thenReturn(cliente);

        Cliente resultado = clienteService.buscar("123.456.789-00");

        assertNotNull(resultado);
        assertEquals("123.456.789-00", resultado.getCpf());
        verify(clienteAdapterPortOut, times(1)).buscar(anyString());
    }

    @Test
    void autenticarClienteDeveValidarCpfComSucesso() {
        doNothing().when(validarCpfLambdaPortOut).validarCpf(anyString());

        clienteService.autenticarCliente("123.456.789-00");

        verify(validarCpfLambdaPortOut, times(1)).validarCpf(anyString());
    }

    @Test
    void autenticarClienteDeveLancarExcecaoQuandoConexaoFalha() {
        doThrow(new RuntimeException("Erro de conexão com o serviço de validação"))
            .when(validarCpfLambdaPortOut).validarCpf(anyString());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            clienteService.autenticarCliente("123.456.789-00");
        });

        assertEquals("Erro de conexão com o serviço de validação", exception.getMessage());
    }


    // New tests for improving coverage
    @Test
    void cadastrarDeveLancarExcecaoQuandoErro() {
        doThrow(new RuntimeException("Error during registration")).when(clienteAdapterPortOut).cadastrar(any(Cliente.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.cadastrar(cliente);
        });

        assertEquals("Error during registration", exception.getMessage());
    }

    @Test
    void buscarDeveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteAdapterPortOut.buscar(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscar("999.999.999-99");
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }


    @Test
    void autenticarClienteDeveLancarExcecaoParaCpfInvalido() {
        doThrow(new IllegalArgumentException("Invalid CPF")).when(validarCpfLambdaPortOut).validarCpf(anyString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.autenticarCliente("invalid-cpf");
        });

        assertEquals("Invalid CPF", exception.getMessage());
    }
}
