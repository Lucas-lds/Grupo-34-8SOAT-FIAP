### ROLE IAM PARA O DYNAMODB ###
resource "aws_iam_role" "db_pedido_dynamodb_access_role" {
  name = "dynamodb-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "eks.amazonaws.com"
        },
        Action = "sts:AssumeRole"
      }
    ]
  })
}

### POLÍTICA IAM PARA O DYNAMODB ###
resource "aws_iam_policy" "db_pedido_dynamodb_access_policy" {
  name        = "DynamoDBPolicy"
  description = "Permite acesso ao DynamoDB"
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "dynamodb:PutItem",
          "dynamodb:GetItem",
          "dynamodb:UpdateItem",
          "dynamodb:DeleteItem",
          "dynamodb:Query",
          "dynamodb:Scan"
        ],
        Resource = "*"
      }
    ]
  })
}

### ANEXAR A POLÍTICA AO ROLE ###
resource "aws_iam_role_policy_attachment" "db_pedido_dynamodb_access_policy_attachment" {
  role       = aws_iam_role.db_pedido_dynamodb_access_role.name
  policy_arn = aws_iam_policy.db_pedido_dynamodb_access_policy.arn
}

### SERVICE ACCOUNT PARA O DYNAMODB ###
resource "kubernetes_service_account" "db_pedido_dynamodb_account" {
  metadata {
    name      = "db-pedido-dynamodb-account"
    namespace = "default"
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.db_pedido_dynamodb_access_role.arn
    }
  }
}