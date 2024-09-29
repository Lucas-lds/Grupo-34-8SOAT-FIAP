CREATE TABLE tb_clientes (
    id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único do cliente
    nome VARCHAR(100) NOT NULL,                -- Nome do cliente
    email VARCHAR(100) UNIQUE NOT NULL,        -- Email do cliente
    telefone VARCHAR(15),                      -- Telefone do cliente
    cpf VARCHAR(14),                           -- cpf do cliente
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP  -- Data e hora de cadastro
);

