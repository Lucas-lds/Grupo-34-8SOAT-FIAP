output "gateway_url" {
  value = aws_api_gateway_rest_api.auth_api.invoke_url  # Exemplo de URL do Gateway API
  description = "URL do API Gateway"
}

output "cognito_user_pool_id" {
  value = aws_cognito_user_pool.restaurante_user_pool.id  # ID do Cognito User Pool
  description = "ID do Cognito User Pool"
}
