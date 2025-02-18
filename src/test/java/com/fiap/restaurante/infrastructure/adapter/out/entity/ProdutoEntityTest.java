package com.fiap.restaurante.infrastructure.adapter.out.entity;

import com.fiap.restaurante.core.domain.Produto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProdutoEntityTest {

    @Test
    void deveConverterParaDomainCorretamente() {
        ProdutoEntity produtoEntity = new ProdutoEntity("Pizza", "Comida", 45.99, "Pizza deliciosa", "http://imagem.com/pizza.jpg");
        produtoEntity.setId(1L);

        Produto produto = produtoEntity.toDomain();

        assertThat(produto.getIdProduto()).isEqualTo(1L);
        assertThat(produto.getNome()).isEqualTo("Pizza");
        assertThat(produto.getCategoria()).isEqualTo("Comida");
        assertThat(produto.getPreco()).isEqualTo(45.99);
        assertThat(produto.getDescricao()).isEqualTo("Pizza deliciosa");
        assertThat(produto.getImagemUrl()).isEqualTo("http://imagem.com/pizza.jpg");
    }

    @Test
    void deveCriarProdutoEntityAPartirDeDomainCorretamente() {
        Produto produto = new Produto(1L, "Hamburguer", "Comida", 29.99, "Hamburguer suculento", "http://imagem.com/hamburguer.jpg");
        ProdutoEntity produtoEntity = ProdutoEntity.fromDomain(produto);

        assertThat(produtoEntity.toDomain().getNome()).isEqualTo("Hamburguer");
        assertThat(produtoEntity.toDomain().getCategoria()).isEqualTo("Comida");
        assertThat(produtoEntity.toDomain().getPreco()).isEqualTo(29.99);
        assertThat(produtoEntity.toDomain().getDescricao()).isEqualTo("Hamburguer suculento");
        assertThat(produtoEntity.toDomain().getImagemUrl()).isEqualTo("http://imagem.com/hamburguer.jpg");
    }

    @Test
    void devePermitirSettersEGetters() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(2L);
        produtoEntity.setNome("Sushi");
        produtoEntity.setCategoria("Comida Japonesa");
        produtoEntity.setPreco(59.99);
        produtoEntity.setDescricao("Sushi variado");
        produtoEntity.setImagemUrl("http://imagem.com/sushi.jpg");

        assertThat(produtoEntity.toDomain().getIdProduto()).isEqualTo(2L);
        assertThat(produtoEntity.toDomain().getNome()).isEqualTo("Sushi");
        assertThat(produtoEntity.toDomain().getCategoria()).isEqualTo("Comida Japonesa");
        assertThat(produtoEntity.toDomain().getPreco()).isEqualTo(59.99);
        assertThat(produtoEntity.toDomain().getDescricao()).isEqualTo("Sushi variado");
        assertThat(produtoEntity.toDomain().getImagemUrl()).isEqualTo("http://imagem.com/sushi.jpg");
    }
}
