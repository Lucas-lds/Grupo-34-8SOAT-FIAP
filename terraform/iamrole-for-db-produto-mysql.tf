### IAM Role para o RDS de produto ###
resource "aws_iam_role" "db_produto_mysql_access_role" {
  name = "iam-role-for-db-produto"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "rds.amazonaws.com"
        },
        Action = "sts:AssumeRole"
      }
    ]
  })
}

### IAM Policy para o RDS de produto ###
resource "aws_iam_policy" "db_produto_mysql_access_policy" {
  name        = "iam-policy-for-db-produto"
  description = "IAM policy for RDS production instance"
  policy      = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents",
          "rds:DescribeDBInstances",
          "rds:Connect",          
          "cloudwatch:PutMetricData"
        ],
        Resource = "*"
      }
    ]
  })
}

### Attach IAM Policy to IAM Role ###
resource "aws_iam_role_policy_attachment" "db_produto_mysql_access_policy_attachment" {
  role       = aws_iam_role.db_produto_mysql_access_role.name
  policy_arn = aws_iam_policy.db_produto_mysql_access_policy.arn
}

### Cria a Service Account no Kubernetes ###
resource "kubernetes_service_account" "db_produto_mysql_service_account" {
  metadata {
    name      = "db-produto-mysql-service-account"
    namespace = "default"
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.db_produto_mysql_access_role.arn
    }
  }
}