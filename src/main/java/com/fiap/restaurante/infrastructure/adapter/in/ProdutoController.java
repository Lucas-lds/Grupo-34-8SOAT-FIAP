package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoUseCasePortOut produtoUseCasePortOut;

    public ProdutoController(ProdutoUseCasePortOut produtoUseCasePortOut){
        this.produtoUseCasePortOut = produtoUseCasePortOut;
    }
    
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoUseCasePortOut.listarProdutos();
    }

    @GetMapping("categoria/{categoria}")
    public List<Produto> buscarPorCategoria(@PathVariable String categoria) {
        return produtoUseCasePortOut.listarProdutoPorCategoria(categoria);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody ProdutoRequest produtoRequest) {
        var produtoCadastrado = produtoUseCasePortOut.criarProduto(produtoRequest.toDomain());
        var response = ProdutoResponse.fromDomain(produtoCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequest produtoDetails) throws BadRequestException {
        var updatedProduto = produtoUseCasePortOut.atualizarProduto(id, produtoDetails.toDomain());
        var response = ProdutoResponse.fromDomain(updatedProduto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoUseCasePortOut.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
