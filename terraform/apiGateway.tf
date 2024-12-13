# API Gateway REST API
resource "aws_api_gateway_rest_api" "auth_api" {
  name        = "AuthAPI"
  description = "API de autenticação para o Restaurante"
}

# Recurso /auth no API Gateway
resource "aws_api_gateway_resource" "auth_resource" {
  rest_api_id = aws_api_gateway_rest_api.auth_api.id
  parent_id   = aws_api_gateway_rest_api.auth_api.root_resource_id
  path_part   = "auth"
}

# Método GET para o recurso /auth
resource "aws_api_gateway_method" "auth_method" {
  rest_api_id   = aws_api_gateway_rest_api.auth_api.id
  resource_id   = aws_api_gateway_resource.auth_resource.id
  http_method   = "GET"
  authorization = "NONE" # Não precisamos de outro tipo de autorização, já que a autenticação é feita pela Lambda
}

# Integração do método com a função Lambda
resource "aws_api_gateway_integration" "auth_integration" {
  rest_api_id             = aws_api_gateway_rest_api.auth_api.id
  resource_id             = aws_api_gateway_resource.auth_resource.id
  http_method             = aws_api_gateway_method.auth_method.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = "arn:aws:apigateway:${var.region}:lambda:path/2015-03-31/functions/${aws_lambda_function.auth_function.arn}/invocations"
}

# Permissão para o API Gateway invocar o Lambda
resource "aws_lambda_permission" "allow_api_gateway" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  principal     = "apigateway.amazonaws.com"
  function_name = aws_lambda_function.auth_function.function_name
}

# Deployment da API
resource "aws_api_gateway_deployment" "auth_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.auth_api.id
  depends_on  = [aws_api_gateway_method.auth_method]  # Certifique-se de que os métodos estão criados antes
  
}

# Criação do estágio da API
resource "aws_api_gateway_stage" "api_stage" {
  stage_name    = "prod"  
  rest_api_id   = aws_api_gateway_rest_api.auth_api.id
  deployment_id = aws_api_gateway_deployment.auth_api_deployment.id
}
