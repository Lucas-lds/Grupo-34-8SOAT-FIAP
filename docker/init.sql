CREATE TABLE tb_clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do cliente
    nome VARCHAR(100) NOT NULL,                -- Nome do cliente
    email VARCHAR(100) UNIQUE NOT NULL,        -- Email do cliente
    telefone VARCHAR(15),                      -- Telefone do cliente
    cpf VARCHAR(14),
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP  -- Data e hora de cadastro
);

CREATE TABLE tb_produtos (
    ProdutoID INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do produto
    Nome VARCHAR(100) NOT NULL,               -- Nome do produto
    Descricao TEXT,                           -- Descrição do produto
    Preco DECIMAL(10, 2) NOT NULL,            -- Preço do produto
    QuantidadeEmEstoque INT NOT NULL          -- Quantidade disponível em estoque
);

CREATE TABLE tb_pedidos (
    PedidoID INT PRIMARY KEY AUTO_INCREMENT,        -- Identificador único do pedido
    id_cliente INT NOT NULL,                         -- Identificador do cliente (chave estrangeira)
    DataPedido DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Data e hora do pedido
    Status VARCHAR(50),                             -- Status do pedido
    FOREIGN KEY (id_cliente) REFERENCES tb_clientes(id)  -- Relacionamento com a tabela Clientes
);

CREATE TABLE tb_itens_pedidos (
    ItemID INT PRIMARY KEY AUTO_INCREMENT,    -- Identificador único do item do pedido
    PedidoID INT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    ProdutoID INT NOT NULL,                  -- Identificador do produto (chave estrangeira)
    Quantidade INT NOT NULL,                 -- Quantidade do produto no pedido
    PrecoUnitario DECIMAL(10, 2) NOT NULL,   -- Preço unitário do produto no momento do pedido
    FOREIGN KEY (PedidoID) REFERENCES tb_pedidos(PedidoID),  -- Relacionamento com a tabela Pedidos
    FOREIGN KEY (ProdutoID) REFERENCES tb_produtos(ProdutoID)  -- Relacionamento com a tabela Produtos
);
