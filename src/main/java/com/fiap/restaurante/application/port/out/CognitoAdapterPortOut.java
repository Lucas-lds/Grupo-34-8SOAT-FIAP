package com.fiap.restaurante.application.port.out;

import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;

public interface CognitoAdapterPortOut {

    void cadastrarClienteCognito(ClienteCognitoRequestDTO cliente);
}
