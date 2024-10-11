-- ==============================
-- TABELAS DO BANCO DE DADOS
-- ==============================

-- Se precisar alterar a estrutura da tabela, utilize comandos ALTER TABLE.
-- Exemplos:
-- ALTER TABLE tb_clientes ADD COLUMN endereco VARCHAR(255);
-- ALTER TABLE tb_produtos MODIFY COLUMN preco DECIMAL(12, 2);

CREATE TABLE IF NOT EXISTS tb_clientes (
    id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do cliente
    nome VARCHAR(100) NOT NULL,                -- Nome do cliente
    email VARCHAR(100) UNIQUE NOT NULL,        -- Email do cliente
    telefone VARCHAR(15),                      -- Telefone do cliente
    cpf VARCHAR(14),                           -- cpf do cliente
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP  -- Data e hora de cadastro
);
CREATE TABLE IF NOT EXISTS tb_produtos (
    id_produto BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do produto
    nome VARCHAR(100) NOT NULL,               -- Nome do produto
    categoria TEXT NOT NULL,                  -- Categoria do produto
    preco DECIMAL(10, 2) NOT NULL,            -- Preço do produto
    descricao TEXT,                           -- Descrição do produto
    imagem_url VARCHAR(255)                   -- URL imagem do produto
);
CREATE TABLE IF NOT EXISTS tb_pedidos (
    id_pedido BIGINT PRIMARY KEY AUTO_INCREMENT,   -- Identificador único do pedido
    id_cliente BIGINT,                   -- Identificador do cliente (chave estrangeira)
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Data e hora do pedido
    status VARCHAR(50),                            -- Status do pedido
    FOREIGN KEY (id_cliente) REFERENCES tb_clientes(id_cliente)  -- Relacionamento com a tabela Clientes
);
CREATE TABLE IF NOT EXISTS tb_pedido_produtos (
    id_pedido_produto BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    id_produto BIGINT NOT NULL,                  -- Identificador do produto (chave estrangeira)
    quantidade INT NOT NULL,                 -- Quantidade do produto no pedido
    FOREIGN KEY (id_pedido) REFERENCES tb_pedidos(id_pedido),  -- Relacionamento com a tabela Pedidos
    FOREIGN KEY (id_produto) REFERENCES tb_produtos(id_produto)  -- Relacionamento com a tabela Produtos
);
CREATE TABLE IF NOT EXISTS tb_pagamentos (
    id_pagamento BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    status VARCHAR(50),                            -- Status do pagamento
    FOREIGN KEY (id_pedido) REFERENCES tb_pedidos(id_pedido)  -- Relacionamento com a tabela Pedidos
);