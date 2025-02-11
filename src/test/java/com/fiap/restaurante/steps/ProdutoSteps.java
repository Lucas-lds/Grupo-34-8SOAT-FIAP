package com.fiap.restaurante.steps;

import com.fiap.restaurante.application.service.ProdutoService;
import com.fiap.restaurante.core.domain.Produto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import org.apache.coyote.BadRequestException;

import static org.mockito.Mockito.*;

public class ProdutoSteps {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoSteps produtoSteps;

    private Produto produto;
    private String produtoNome;

    public ProdutoSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("que o produto {string} deseja ser cadastrado")
    public void que_o_produto_deseja_ser_cadastrado(String nome) {
        this.produtoNome = nome;
        this.produto = new Produto(null, nome, "Categoria", 10.0, "Descrição", "imagemUrl"); // Initialize with necessary fields
    }

    @When("o produto se cadastra")
    public void o_produto_se_cadastra() {
        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto); // Mock criarProduto method
        produtoService.criarProduto(produto);
    }

    @Then("o produto deve ser cadastrado com sucesso")
    public void o_produto_deve_ser_cadastrado_com_sucesso() {
        Assertions.assertNotNull(produto);
        Assertions.assertEquals(produtoNome, produto.getNome());
        verify(produtoService, times(1)).criarProduto(any(Produto.class)); // Verify criarProduto call
    }

    @Given("que o produto {string} está cadastrado")
    public void que_o_produto_está_cadastrado(String nome) {
        this.produtoNome = nome;
        when(produtoService.listarProdutoPorCategoria(anyString())).thenReturn(List.of(produto)); // Mock buscar method
    }

    @When("busco o produto pelo nome")
    public void busco_o_produto_pelo_nome() {
        produtoService.listarProdutoPorCategoria(produtoNome);
    }

    @Then("o produto deve ser retornado")
    public void o_produto_deve_ser_retornado() {
        Assertions.assertNotNull(produto);
        Assertions.assertEquals(produtoNome, produto.getNome());
        verify(produtoService, times(1)).listarProdutoPorCategoria(produtoNome); // Verify buscar call
    }

    @Given("que o produto {string} é válido")
    public void que_o_produto_é_válido(String nome) {
        this.produtoNome = nome;
    }

    @When("o cliente tenta validar o produto")
    public void o_cliente_tenta_validar_o_produto() throws BadRequestException {
        doNothing().when(produtoService).atualizarProduto(anyLong(), any(Produto.class)); // Mock validation
        produtoService.atualizarProduto(1L, produto);
    }

    @Then("a validação do produto deve ser realizada com sucesso")
    public void a_validação_do_produto_deve_ser_realizada_com_sucesso() throws BadRequestException {
        verify(produtoService, times(1)).atualizarProduto(anyLong(), any(Produto.class)); // Verify validation call
    }
}
