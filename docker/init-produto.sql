CREATE TABLE IF NOT EXISTS tb_produtos (
    id_produto BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do produto
    nome VARCHAR(100) NOT NULL,               -- Nome do produto
    categoria TEXT NOT NULL,                  -- Categoria do produto
    preco DECIMAL(10, 2) NOT NULL,            -- Preço do produto
    descricao TEXT,                           -- Descrição do produto
    imagem_url VARCHAR(255)                   -- URL imagem do produto
);