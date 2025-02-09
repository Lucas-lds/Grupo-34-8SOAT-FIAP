package com.fiap.restaurante.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import com.fiap.restaurante.infrastructure.exception.EmailDuplicadoException;
import com.fiap.restaurante.infrastructure.exception.ClienteNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteAdapterOutTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteAdapterOut clienteAdapterOut;

    private Cliente cliente;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "123.456.789-00", "João", "joao@example.com", "12345");
        clienteEntity = ClienteEntity.fromDomain(cliente);
    }

    @Test
    void buscarDeveRetornarClienteQuandoEncontrado() {
        when(clienteRepository.findFirstByCpf(anyString())).thenReturn(Optional.of(clienteEntity));

        Cliente resultado = clienteAdapterOut.buscar("123.456.789-00");

        assertNotNull(resultado);
        assertEquals("123.456.789-00", resultado.getCpf());
        assertEquals("João", resultado.getNome());
        verify(clienteRepository, times(1)).findFirstByCpf(anyString());
    }

    @Test
    void buscarDeveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findFirstByCpf(anyString())).thenReturn(Optional.empty());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteAdapterOut.buscar("123.456.789-00");
        });

        assertEquals("Cliente com CPF 123.456.789-00 não encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findFirstByCpf(anyString());
    }

    @Test
    void cadastrarDeveRetornarClienteQuandoCadastradoComSucesso() {
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        Cliente resultado = clienteAdapterOut.cadastrar(cliente);

        assertNotNull(resultado);
        assertEquals("123.456.789-00", resultado.getCpf());
        assertEquals("João", resultado.getNome());
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void cadastrarDeveLancarExcecaoQuandoEmailJaExistir() {
        when(clienteRepository.save(any(ClienteEntity.class))).thenThrow(DataIntegrityViolationException.class);

        EmailDuplicadoException exception = assertThrows(EmailDuplicadoException.class, () -> {
            clienteAdapterOut.cadastrar(cliente);
        });

        assertEquals("O e-mail fornecido já está cadastrado.", exception.getMessage());
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }
}
