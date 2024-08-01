CREATE TABLE tb_clientes (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do cliente
    nome VARCHAR(100) NOT NULL,                -- Nome do cliente
    email VARCHAR(100) UNIQUE NOT NULL,        -- Email do cliente
    telefone VARCHAR(15),                      -- Telefone do cliente
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP  -- Data e hora de cadastro
);

CREATE TABLE tb_produtos (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do produto
    nome VARCHAR(100) NOT NULL,               -- Nome do produto
    categoria TEXT,                           -- Categoria do produto
    preco DECIMAL(10, 2) NOT NULL,            -- Preço do produto
    descricao TEXT                            -- Descrição do produto
);

CREATE TABLE tb_pedidos (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,   -- Identificador único do pedido
    id_cliente INT NOT NULL,                   -- Identificador do cliente (chave estrangeira)
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Data e hora do pedido
    status_ VARCHAR(50),                            -- Status do pedido                      
    FOREIGN KEY (id_cliente) REFERENCES tb_clientes(id_cliente)  -- Relacionamento com a tabela Clientes
);

CREATE TABLE tb_pedido_produtos (
    id_pedido_produto INT PRIMARY KEY AUTO_INCREMENT,    -- Identificador único do item do pedido
    id_pedido INT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    id_produto INT NOT NULL,                  -- Identificador do produto (chave estrangeira)
    quantidade INT NOT NULL,                 -- Quantidade do produto no pedido
    FOREIGN KEY (id_pedido) REFERENCES tb_pedidos(id_pedido),  -- Relacionamento com a tabela Pedidos
    FOREIGN KEY (id_produto) REFERENCES tb_produtos(id_produto)  -- Relacionamento com a tabela Produtos

);
