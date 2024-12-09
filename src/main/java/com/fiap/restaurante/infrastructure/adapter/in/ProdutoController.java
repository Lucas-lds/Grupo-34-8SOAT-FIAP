package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto", description = "Operações de produtos")
public class ProdutoController {

    private final ProdutoUseCasePortOut produtoUseCasePortOut;

    public ProdutoController(ProdutoUseCasePortOut produtoUseCasePortOut) {
        this.produtoUseCasePortOut = produtoUseCasePortOut;
    }

    @Operation(summary = "Listar todos os produtos", description = "Lista todos os produtos disponíveis no sistema.")
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoUseCasePortOut.listarProdutos();
    }

    @Operation(summary = "Buscar produtos por categoria", description = "Busca produtos filtrados pela categoria.")
    @GetMapping("categoria/{categoria}")
    public List<Produto> buscarPorCategoria(@PathVariable String categoria) {
        return produtoUseCasePortOut.listarProdutoPorCategoria(categoria);
    }

    @Operation(summary = "Cadastrar um novo produto", description = "Cadastra um novo produto no sistema.")
    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody ProdutoRequest produtoRequest) {
        var produtoCadastrado = produtoUseCasePortOut.criarProduto(produtoRequest.toDomain());
        var response = ProdutoResponse.fromDomain(produtoCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto existente.")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequest produtoDetails) throws BadRequestException {
        var updatedProduto = produtoUseCasePortOut.atualizarProduto(id, produtoDetails.toDomain());
        var response = ProdutoResponse.fromDomain(updatedProduto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar produto", description = "Deleta um produto pelo seu ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoUseCasePortOut.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
