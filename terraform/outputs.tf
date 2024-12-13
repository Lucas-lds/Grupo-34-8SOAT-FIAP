output "gateway_url" {
  value = aws_api_gateway_rest_api.auth_api.execution_arn  # Exemplo de URL do Gateway API
  description = "URL do API Gateway"
}

output "cognito_user_pool_id" {
  value = aws_cognito_user_pool.restaurante_user_pool.id  # ID do Cognito User Pool
  description = "ID do Cognito User Pool"
}

output "region" {
  value       = data.aws_region.current.name
  description = "A região configurada no AWS"
}
