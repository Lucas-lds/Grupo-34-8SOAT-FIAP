package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

@Component
public class CognitoAdapterOut implements CognitoAdapterPortOut {

    private final String userPoolId = "sa-east-1_m3na6PIro";
    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoAdapterOut() {
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }


    @Override
    public void cadastrarClienteCognito(ClienteCognitoRequestDTO cliente) {
        try {
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(cliente.nome())
                    .userAttributes(
                            AttributeType.builder().name("custom:cpf").value(cliente.cpf()).build(),
                            AttributeType.builder().name("email").value(cliente.email()).build()
                    )
                    .messageAction("SUPPRESS")
                    .build();

            cognitoClient.adminCreateUser(createUserRequest);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário no Cognito", e);
        }
    }
}

