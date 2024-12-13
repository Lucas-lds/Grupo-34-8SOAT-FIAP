# Criando uma Policy do IAM para Secrets Manager
resource "aws_iam_policy" "secrets_manager_policy" {
  name        = "LambdaSecretsManagerPolicy"
  description = "Permite o acesso aos segredos do Secrets Manager"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "secretsmanager:GetSecretValue",
          "secretsmanager:DescribeSecret"
        ],
        Resource : [
          "arn:aws:secretsmanager:${var.region}:717279688908:secret:USER_POOL_ID-*",
          "arn:aws:secretsmanager:${var.region}:717279688908:secret:api.gateway.url-*"
        ]
      }
    ]
  })
}

# Anexando a Policy ao Role
resource "aws_iam_role_policy_attachment" "lambda_secrets_manager_policy_attachment" {
  role       = aws_iam_role.lambda_exec_role.name # Usando a role existente
  policy_arn = aws_iam_policy.secrets_manager_policy.arn
}