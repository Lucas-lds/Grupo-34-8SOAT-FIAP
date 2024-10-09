# Define o Role IAM para acesso ao ECR
resource "aws_iam_role" "ecr_access_role" {
  name = "ecr-access-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "eks.amazonaws.com",
        },
        Action = "sts:AssumeRole",
      },
    ],
  })
}

# Define uma política para permitir que os nós acessem o ECR
resource "aws_iam_policy" "eks_node_ecr_policy" {
  name        = "eks-node-ecr-policy"
  description = "Policy to allow EKS nodes to pull images from ECR"
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "ecr:GetAuthorizationToken",
          "ecr:BatchGetImage",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchCheckLayerAvailability",
        ],
        Resource = "*",  # Pode ser restrito a um repositório específico
      },
    ],
  })
}

# Anexa a política ao Role
resource "aws_iam_role_policy_attachment" "ecr_access_policy_attachment" {
  role       = aws_iam_role.ecr_access_role.name
  policy_arn = aws_iam_policy.eks_node_ecr_policy.arn
}
