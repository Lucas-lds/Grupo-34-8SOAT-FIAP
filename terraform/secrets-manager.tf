# Segredo para USER_POOL_ID no AWS Secrets Manager
resource "aws_secretsmanager_secret" "user_pool_id" {
  name        = "USER_POOL_ID"
  description = "Secret containing the USER_POOL_ID for Cognito"
}

resource "aws_secretsmanager_secret_version" "user_pool_id_value" {
  secret_id = aws_secretsmanager_secret.user_pool_id.id
  secret_string = jsonencode({
    "USER_POOL_ID" = aws_cognito_user_pool.restaurante_user_pool.id
  })
}

# Segredo para api.gateway.url no AWS Secrets Manager
resource "aws_secretsmanager_secret" "api_gateway_url" {
  name        = "api.gateway.url"
  description = "Secret containing the API Gateway URL"
}

resource "aws_secretsmanager_secret_version" "api_gateway_url_value" {
  secret_id = aws_secretsmanager_secret.api_gateway_url.id
  secret_string = jsonencode({
    "api.gateway.url" = aws_api_gateway_stage.api_stage.invoke_url
  })
}