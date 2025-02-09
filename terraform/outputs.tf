output "gateway_url" {
  value       = aws_api_gateway_rest_api.auth_api.execution_arn # Exemplo de URL do Gateway API
  description = "URL do API Gateway"
}

output "cognito_user_pool_id" {
  value       = aws_cognito_user_pool.restaurante_user_pool.id # ID do Cognito User Pool
  description = "ID do Cognito User Pool"
}

### DNS NAME DATA SOURCE ###
output "URL" {
  value = length(kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress) > 0 ? kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress[0].hostname : "Ingress não disponível"
}