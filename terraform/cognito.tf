# Cognito User Pool
resource "aws_cognito_user_pool" "restaurante_user_pool" {
  name = "restaurante-user-pool"

  lifecycle {
    ignore_changes = [
      schema, # Ignora mudanças nos atributos do schema
    ]
  }

  schema {
    name                = "custom:custom:cpf"
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

  generate_secret = false

  # Definindo um fluxo de autenticação personalizado
  explicit_auth_flows = [
    "ALLOW_CUSTOM_AUTH",
    "ALLOW_REFRESH_TOKEN_AUTH",
    "ALLOW_USER_PASSWORD_AUTH"
  ]

  # Definindo a validade dos tokens
  access_token_validity  = 1  # Validade do token de acesso em minutos
  id_token_validity      = 1  # Validade do token de ID em minutos
  refresh_token_validity = 60 # Validade do token de atualização em dias
}

# Cognito User
# Cognito User
resource "aws_cognito_user" "restaurante_user" {
  user_pool_id = aws_cognito_user_pool.restaurante_user_pool.id
  username     = "teste"
  attributes = {
    email       = "usuario_exemplo@example.com"
    "custom:custom:cpf"  = "12345678900"
  }
  temporary_password = "SenhaTemporaria123!"
  force_alias_creation = false
  message_action = "SUPPRESS"
  lifecycle {
    ignore_changes = [
      attributes,
      temporary_password,
      username
    ]
  }
}

# Definir a senha permanente para o usuário
resource "null_resource" "set_user_password" {
  provisioner "local-exec" {
    command = <<EOT
      aws cognito-idp admin-set-user-password --user-pool-id ${aws_cognito_user_pool.restaurante_user_pool.id} --username teste --password "SenhaPermanente123!" --permanent
    EOT
  }

  depends_on = [aws_cognito_user.restaurante_user]
}
