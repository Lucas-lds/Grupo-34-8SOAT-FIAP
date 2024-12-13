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
resource "aws_iam_policy" "lambda_policy" {
  name = "lambda-policy"
  description = "A política que dá permissão à Lambda para acessar o Cognito"
  #role = aws_iam_role.lambda_exec_role.id


  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action   = ["cognito-idp:ListUsers"], # Permite a ação ListUsers no Cognito
        Effect   = "Allow",
        Resource = aws_cognito_user_pool.restaurante_user_pool.arn # Restrição para o User Pool específico
      }
    ]
  })
}


# Anexando as duas políticas à role da Lambda
resource "aws_iam_role_policy_attachment" "lambda_policy_attachment" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = aws_iam_policy.lambda_policy.arn
}