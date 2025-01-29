CREATE TABLE IF NOT EXISTS tb_pagamentos (
    id_pagamento BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,                   -- Identificador do pedido (chave estrangeira)
    status VARCHAR(50),                            -- Status do pagamento
);