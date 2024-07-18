package com.fiap.restaurante.application.port.out;

import com.fiap.restaurante.core.domain.Cliente;

public interface BuscarClienteAdapterPortOut {
    Cliente buscar(String cpf);
}
