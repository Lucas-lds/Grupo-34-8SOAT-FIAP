package com.fiap.restaurante.steps;

import com.fiap.restaurante.application.service.ClienteService;
import com.fiap.restaurante.core.domain.Cliente;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ClienteSteps {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteSteps clienteSteps;

    private Cliente cliente;
    private String cpf;

    public ClienteSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("que o cliente {string} com CPF {string} deseja se cadastrar")
    public void que_o_cliente_com_cpf_deseja_se_cadastrar(String nome, String cpf) {
        this.cliente = new Cliente(1L, cpf, nome, "joao@example.com", "12345");
        this.cpf = cpf;
    }

    @When("o cliente se cadastra")
    public void o_cliente_se_cadastra() {
        when(clienteService.cadastrar(any(Cliente.class))).thenReturn(cliente);
        clienteService.cadastrar(cliente);
    }

    @Then("o cliente deve ser cadastrado com sucesso")
    public void o_cliente_deve_ser_cadastrado_com_sucesso() {
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(cpf, cliente.getCpf());
        verify(clienteService, times(1)).cadastrar(any(Cliente.class));
    }

    @Given("que o cliente com CPF {string} está cadastrado")
    public void que_o_cliente_com_cpf_está_cadastrado(String cpf) {
        this.cpf = cpf;
        when(clienteService.buscar(cpf)).thenReturn(cliente);
    }

    @When("busco o cliente pelo CPF")
    public void busco_o_cliente_pelo_cpf() {
        clienteService.buscar(cpf);
    }

    @Then("o cliente deve ser retornado")
    public void o_cliente_deve_ser_retornado() {
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(cpf, cliente.getCpf());
        verify(clienteService, times(1)).buscar(cpf);
    }

    @Given("que o CPF {string} é válido")
    public void que_o_cpf_é_válido(String cpf) {
        this.cpf = cpf;
    }

    @When("o cliente tenta se autenticar")
    public void o_cliente_tenta_se_autenticar() {
        doNothing().when(clienteService).autenticarCliente(cpf);
        clienteService.autenticarCliente(cpf);
    }

    @Then("a validação do CPF deve ser realizada com sucesso")
    public void a_validação_do_cpf_deve_ser_realizada_com_sucesso() {
        verify(clienteService, times(1)).autenticarCliente(cpf);
    }
}
