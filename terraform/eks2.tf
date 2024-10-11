resource "aws_eks_cluster" "eks-cluster" {
  name                      = var.cluster_name
  role_arn                  = aws_iam_role.eks_node_role.arn
  version                   = "1.31"
  enabled_cluster_log_types = ["api", "audit", "authenticator", "controllerManager", "scheduler"]


  vpc_config {
    security_group_ids      = [aws_security_group.ssh_cluster.id]
    subnet_ids              = ["subnet-0ba1d16a81898b46b", "subnet-02e5a2960cbaf6259"]
    endpoint_private_access = true
    endpoint_public_access  = true
  }
}

### Client OIDC
data "tls_certificate" "oidc" {
  url = aws_eks_cluster.eks-cluster.identity[0].oidc[0].issuer
}

resource "aws_eks_node_group" "eks-cluster" {
  cluster_name    = aws_eks_cluster.eks-cluster.name
  node_group_name = "restaurante2-nodegroup"
  node_role_arn   = aws_iam_role.eks_node_role.arn
  subnet_ids      = ["subnet-0ba1d16a81898b46b", "subnet-02e5a2960cbaf6259"]
  instance_types  = ["t3.medium"]
  ami_type        = "BOTTLEROCKET_x86_64"
  version         = "1.31"


  scaling_config {
    desired_size = 3
    max_size     = 4
    min_size     = 3
  }

}

### Criação do LogGroup no CloudWatch
/*
resource "aws_cloudwatch_log_group" "cluster-log-group" {
  name              = "/aws/eks/${var.cluster_name}/cluster"
  retention_in_days = 7

}
*/