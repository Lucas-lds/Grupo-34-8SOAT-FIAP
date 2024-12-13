#### IAM ROLES ####

# Define um Role IAM que o Amazon EKS usará para assumir permissões
resource "aws_iam_role" "eks_role" {
  name = "eks-cluster-role" # Nome do Role IAM para o cluster EKS

  # Política de AssumeRole que permite ao serviço EKS assumir este Role
  assume_role_policy = jsonencode({
    Version = "2012-10-17", # Versão da política de assume role
    Statement = [
      {
        Effect = "Allow", # Permite a ação
        Principal = {
          Service = "eks.amazonaws.com", # O serviço EKS é o principal que pode assumir este Role
        },
        Action = "sts:AssumeRole", # Ação permitida é assumir o Role
      },
    ],
  })
}

#### IAM POLICIES ####

# Associate IAM Policy to IAM Role
resource "aws_iam_role_policy_attachment" "eks-AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.eks_role.name
}

resource "aws_iam_role_policy_attachment" "eks-AmazonEKSVPCResourceController" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
  role       = aws_iam_role.eks_role.name
}

resource "aws_iam_policy" "custom_load_balancer_policy" {
  name        = "CustomLoadBalancerPolicy"
  description = "Custom policy for LoadBalancer access"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "elasticloadbalancing:*",
          "ec2:DescribeSubnets",
          "ec2:DescribeSecurityGroups",
          "ec2:CreateSecurityGroup",
          "ec2:CreateTags",
          "ec2:AuthorizeSecurityGroupIngress",
          "ec2:AuthorizeSecurityGroupEgress",
          "ec2:DeleteSecurityGroup",
          "ec2:RevokeSecurityGroupIngress",
          "ec2:RevokeSecurityGroupEgress",
        ],
        Resource = "*",
      },
      {
        Effect = "Allow",
        Action = [
          "ec2:DescribeInstances",
        ],
        Resource = "*",
      },
    ],
  })
}

resource "aws_iam_role_policy_attachment" "attach_custom_policy" {
  role       = aws_iam_role.eks_role.name
  policy_arn = aws_iam_policy.custom_load_balancer_policy.arn
}



resource "aws_iam_policy" "eks_secrets_manager_policy" {
  name        = "EKSSecretsManagerPolicy"
  description = "Policy for accessing AWS Secrets Manager"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "secretsmanager:GetSecretValue",
        ],
        Resource = "*",  
      },
    ],
  })
}

# Anexa a política ao role do EKS
resource "aws_iam_role_policy_attachment" "eks_secrets_manager_attachment" {
  role       = aws_iam_role.eks_role.name
  policy_arn = aws_iam_policy.eks_secrets_manager_policy.arn
}