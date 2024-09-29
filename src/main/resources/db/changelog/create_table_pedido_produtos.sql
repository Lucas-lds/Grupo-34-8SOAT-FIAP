CREATE TABLE tb_pedido_produtos (
    id_pedido_produto BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    id_produto BIGINT NOT NULL,                  -- Identificador do produto (chave estrangeira)
    quantidade INT NOT NULL,                 -- Quantidade do produto no pedido
    FOREIGN KEY (id_pedido) REFERENCES tb_pedidos(id_pedido),  -- Relacionamento com a tabela Pedidos
    FOREIGN KEY (id_produto) REFERENCES tb_produtos(id_produto)  -- Relacionamento com a tabela Produtos

);