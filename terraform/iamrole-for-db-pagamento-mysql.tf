### IAM Role para o RDS de Pagamento ###
resource "aws_iam_role" "db_pagamento_mysql_access_role" {
  name = "iam-role-for-db-pagamento-mysql"

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

### IAM Policy para o RDS de Pagamento ###
resource "aws_iam_policy" "db_pagamento_mysql_access_policy" {
  name        = "iam-policy-for-db-pagamento-mysql"
  description = "IAM policy for RDS payment instance"
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
resource "aws_iam_role_policy_attachment" "db_pagamento_mysql_access_policy_attachment" {
  role       = aws_iam_role.db_pagamento_mysql_access_role.name
  policy_arn = aws_iam_policy.db_pagamento_mysql_access_policy.arn
}

### Cria a Service Account no Kubernetes ###
resource "kubernetes_service_account" "db_pagamento_mysql_service_account" {
  metadata {
    name      = "db-pagamento-mysql-service-account"
    namespace = "default"
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.db_pagamento_mysql_access_role.arn
    }
  }
}