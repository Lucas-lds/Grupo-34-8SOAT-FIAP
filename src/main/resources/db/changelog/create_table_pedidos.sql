CREATE TABLE tb_pedidos (
    id_pedido BIGINT PRIMARY KEY AUTO_INCREMENT,   -- Identificador único do pedido
    id_cliente BIGINT,                   -- Identificador do cliente (chave estrangeira)
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Data e hora do pedido
    status VARCHAR(50),                            -- Status do pedido
    FOREIGN KEY (id_cliente) REFERENCES tb_clientes(id_cliente)  -- Relacionamento com a tabela Clientes
);