package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import com.fiap.restaurante.infrastructure.exception.ClientePossuiCadastroCognito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

@Component
public class CognitoAdapterOut implements CognitoAdapterPortOut {

    @Value("${aws.cognito.userPoolId:}")
    private String userPoolId;

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

        }
        catch (UsernameExistsException e){
            throw new ClientePossuiCadastroCognito("Cliente já possui cadastro no cognito");
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário no Cognito", e);
        }
    }
}
