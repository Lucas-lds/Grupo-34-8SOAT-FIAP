output "gateway_url" {
  value = "https://${aws_api_gateway_rest_api.auth_api.id}.execute-api.${var.region}.amazonaws.com/${aws_api_gateway_deployment.auth_api_deployment.stage_name}"
  description = "URL pública do API Gateway"
}

output "cognito_user_pool_id" {
  value       = aws_cognito_user_pool.restaurante_user_pool.id # ID do Cognito User Pool
  description = "ID do Cognito User Pool"
}

### DNS NAME DATA SOURCE ###
#output "URL" {
#  value = length(kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress) > 0 ? kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress[0].hostname : "Ingress não disponível"
#}

# Outputs
output "cognito_username" {
  value = aws_cognito_user.restaurante_user.username
}

output "cognito_temporary_password" {
  value     = aws_cognito_user.restaurante_user.temporary_password
  sensitive = true
}

output "mysql_rds_endpoint" {
  value = aws_db_instance.mysql_rds.endpoint
}