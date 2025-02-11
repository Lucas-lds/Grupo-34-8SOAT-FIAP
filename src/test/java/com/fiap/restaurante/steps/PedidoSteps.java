package com.fiap.restaurante.steps;

import com.fiap.restaurante.application.service.PedidoService;
import java.util.UUID;
import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Pedido;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PedidoSteps {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoSteps pedidoSteps;

    private Pedido pedido;
    private String pedidoId;

    public PedidoSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("que o pedido com ID {string} deseja ser cadastrado")
    public void que_o_pedido_com_id_deseja_ser_cadastrado(String id) {
        this.pedidoId = id;
        this.pedido = new Pedido(); // Initialize with necessary fields
        this.pedido.setId(UUID.fromString(id)); // Ensure ID is set as UUID
    }

    @When("o pedido se cadastra")
    public void o_pedido_se_cadastra() {
        when(pedidoService.checkoutPedido(any(Pedido.class))).thenReturn(pedido); // Mock checkout method
        pedidoService.checkoutPedido(pedido);
    }

    @Then("o pedido deve ser cadastrado com sucesso")
    public void o_pedido_deve_ser_cadastrado_com_sucesso() {
        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(pedidoId, String.valueOf(pedido.getId()));
        verify(pedidoService, times(1)).checkoutPedido(any(Pedido.class)); // Verify checkout call
    }

    @Given("que o pedido com ID {string} está cadastrado")
    public void que_o_pedido_com_id_está_cadastrado(String id) {
        this.pedidoId = id;
        when(pedidoService.listarPedidoPorId(UUID.fromString(pedidoId))).thenReturn(pedido); // Mock listing method
    }

    @When("busco o pedido pelo ID")
    public void busco_o_pedido_pelo_id() {
        pedidoService.listarPedidoPorId(UUID.fromString(pedidoId));
    }

    @Then("o pedido deve ser retornado")
    public void o_pedido_deve_ser_retornado() {
        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(pedidoId, String.valueOf(pedido.getId()));
        verify(pedidoService, times(1)).listarPedidoPorId(UUID.fromString(pedidoId)); // Verify listing call
    }

    @Given("que o pedido com ID {string} é válido")
    public void que_o_pedido_com_id_é_válido(String id) {
        this.pedidoId = id;
    }

    @When("o cliente tenta validar o pedido")
    public void o_cliente_tenta_validar_o_pedido() throws BadRequestException {
        doNothing().when(pedidoService).atualizarStatusPedido(anyInt(), any(UUID.class)); // Mock status update
        pedidoService.atualizarStatusPedido(1, UUID.fromString(pedidoId));
    }

    @Then("a validação do pedido deve ser realizada com sucesso")
    public void a_validação_do_pedido_deve_ser_realizada_com_sucesso() throws BadRequestException {
        verify(pedidoService, times(1)).atualizarStatusPedido(anyInt(), any(UUID.class)); // Verify status update call
    }
}
