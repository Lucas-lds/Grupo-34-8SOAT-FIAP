### GRUPO DE SEGURANÇA DO EKS (ssh_cluster)  ###

# Ingress: Permite acesso SSH (porta 22) de qualquer lugar (ou pode ser restrito a IPs específicos).
# Egress: Permite tráfego de saída para qualquer lugar.
resource "aws_security_group" "ssh_cluster_restaurante" {
  name   = "ssh_cluster"
  vpc_id = "vpc-0bb3319f6293b884b"
}

resource "aws_security_group_rule" "ssh_cluster_in" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"] #0.0.0.0 - 255.255.255.255
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

resource "aws_security_group_rule" "https" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"] #0.0.0.0 - 255.255.255.255
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

resource "aws_security_group_rule" "http_80" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"] # Permitir tráfego de qualquer lugar
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

resource "aws_security_group_rule" "http" {
  type              = "ingress"
  from_port         = 8080
  to_port           = 8080
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"] # Permitir tráfego de qualquer lugar
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

resource "aws_security_group_rule" "rds" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"] # Permitir tráfego de qualquer lugar
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

resource "aws_security_group_rule" "ssh_cluster_out" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"] #0.0.0.0 - 255.255.255.255
  security_group_id = aws_security_group.ssh_cluster_restaurante.id
}

### GRUPO DE SEGURANÇA DO RDS (rds_sg) ###

# Ingress: Permite tráfego na porta 3306 apenas do grupo de segurança do EKS.
# Egress: Permite tráfego de saída para qualquer lugar (pode ser ajustado conforme necessário).

resource "aws_security_group" "rds_sg" {
  name        = "rds-security-group"
  description = "Security group for RDS"
  vpc_id      = "vpc-0bb3319f6293b884b"
}

resource "aws_security_group_rule" "rds_in" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  security_group_id = aws_security_group.rds_sg.id
  #source_security_group_id = aws_security_group.ssh_cluster.id ## Permitir acesso do EKS
  cidr_blocks = ["172.31.0.0/16"]
}

resource "aws_security_group_rule" "rds_out" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  security_group_id = aws_security_group.rds_sg.id
  cidr_blocks       = ["0.0.0.0/0"]
}
