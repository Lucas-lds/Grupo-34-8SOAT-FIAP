# Cognito User Pool
resource "aws_cognito_user_pool" "restaurante_user_pool" {
  name = "restaurante-user-pool"

  lifecycle {
    ignore_changes = [
      schema, # Ignora mudanças nos atributos do schema
    ]
  }

  schema {
    name                = "custom:cpf"
    attribute_data_type = "String"
    mutable             = true
    required            = false
  }

  # Se quiser validar automaticamente o e-mail, adicione "email" aqui
  auto_verified_attributes = [] # Estamos apenas verificando o CPF
}

# Cognito User Pool Client
resource "aws_cognito_user_pool_client" "restaurante_app_client" {
  name         = "restaurante-app-client"
  user_pool_id = aws_cognito_user_pool.restaurante_user_pool.id

  generate_secret = true

  # Definindo um fluxo de autenticação personalizado
  explicit_auth_flows = [
    "ALLOW_CUSTOM_AUTH",
    "ALLOW_REFRESH_TOKEN_AUTH" # Adicionando o fluxo de refresh token
  ]
}


