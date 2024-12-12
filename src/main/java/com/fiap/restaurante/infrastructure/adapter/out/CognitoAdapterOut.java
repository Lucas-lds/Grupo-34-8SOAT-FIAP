package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

@Component
public class CognitoAdapterOut implements CognitoAdapterPortOut {

    @Value("${user_pool_id:}")
    private String userPoolId;

    private final CognitoIdentityProviderClient cognitoClient;
    private final RetryTemplate retryTemplate;

    public CognitoAdapterOut(RetryTemplate retryTemplate) {
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        this.retryTemplate = retryTemplate;
    }

    @PostConstruct
    public void init() {
        retryTemplate.execute(context -> {
            if (userPoolId == null || userPoolId.isEmpty()) {
                throw new RuntimeException("O User Pool ID não está configurado.");
            }
            return null;
        });
    }

    @Override
    @Retryable(value = RuntimeException.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
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
