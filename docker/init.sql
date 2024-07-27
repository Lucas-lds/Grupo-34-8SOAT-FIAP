CREATE TABLE Clientes (
    ClienteID INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do cliente
    Nome VARCHAR(100) NOT NULL,                -- Nome do cliente
    Email VARCHAR(100) UNIQUE NOT NULL,        -- Email do cliente
    Telefone VARCHAR(15),                      -- Telefone do cliente
    DataCadastro DATETIME DEFAULT CURRENT_TIMESTAMP  -- Data e hora de cadastro
);

CREATE TABLE Produtos (
    ProdutoID INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do produto
    Nome VARCHAR(100) NOT NULL,               -- Nome do produto
    Descricao TEXT,                           -- Descrição do produto
    Preco DECIMAL(10, 2) NOT NULL,            -- Preço do produto
    QuantidadeEmEstoque INT NOT NULL          -- Quantidade disponível em estoque
);

CREATE TABLE Pedidos (
    PedidoID INT PRIMARY KEY AUTO_INCREMENT,   -- Identificador único do pedido
    ClienteID INT NOT NULL,                   -- Identificador do cliente (chave estrangeira)
    DataPedido DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Data e hora do pedido
    Status VARCHAR(50),                        -- Status do pedido
    FOREIGN KEY (ClienteID) REFERENCES Clientes(ClienteID)  -- Relacionamento com a tabela Clientes
);

CREATE TABLE ItensPedido (
    ItemID INT PRIMARY KEY AUTO_INCREMENT,    -- Identificador único do item do pedido
    PedidoID INT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    ProdutoID INT NOT NULL,                  -- Identificador do produto (chave estrangeira)
    Quantidade INT NOT NULL,                 -- Quantidade do produto no pedido
    PrecoUnitario DECIMAL(10, 2) NOT NULL,   -- Preço unitário do produto no momento do pedido
    FOREIGN KEY (PedidoID) REFERENCES Pedidos(PedidoID),  -- Relacionamento com a tabela Pedidos
    FOREIGN KEY (ProdutoID) REFERENCES Produtos(ProdutoID)  -- Relacionamento com a tabela Produtos
);
