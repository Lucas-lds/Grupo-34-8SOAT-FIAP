# Role que permite que o Lambda acesse o Cognito
resource "aws_iam_role" "lambda_exec_role" {
  name = "lambda_exec_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Política que restringe o acesso da Lambda ao User Pool específico do Cognito
resource "aws_iam_role_policy" "lambda_policy" {
  name = "lambda-policy"
  role = aws_iam_role.lambda_exec_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "cognito-idp:ListUsers",
          "cognito-idp:AdminInitiateAuth",
          "cognito-idp:InitiateAuth"
        ],
        Effect   = "Allow",
        Resource = aws_cognito_user_pool.restaurante_user_pool.arn # Restrição para o User Pool específico
      }
    ]
  })
}