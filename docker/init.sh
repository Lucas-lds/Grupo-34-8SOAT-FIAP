#!/bin/bash

# Buscar os segredos do AWS Secrets Manager e extrair os valores usando jq
USER_POOL_ID=$(aws secretsmanager get-secret-value --secret-id "USER_POOL_ID" --query "SecretString" --output text | jq -r .USER_POOL_ID)
API_GATEWAY_URL=$(aws secretsmanager get-secret-value --secret-id "api.gateway.url" --query "SecretString" --output text | jq -r .api.gateway.url)

# Substituir as variáveis no arquivo application.properties
sed -i "s|\${USER_POOL_ID}|${USER_POOL_ID}|" /app/application.properties
sed -i "s|\${API_GATEWAY_URL}|${API_GATEWAY_URL}|" /app/application.properties

# Executar a aplicação Java
exec java -jar /app/app.jar

