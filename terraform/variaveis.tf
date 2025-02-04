variable "nome_repositorio" {
  type    = string
  default = "restaurante-repositorio"
}

variable "cluster_name" {
  type    = string
  default = "restaurante-cluster2"
}

variable "mysql_user" {
  description = "MySQL User"
  type        = string
  default     = "restaurante_user"
}

variable "mysql_password" {
  description = "MySQL Password"
  type        = string
  default     = "restaurante_user_pass"
}

variable "mysql_database" {
  description = "MySQL Database"
  type        = string
  default     = "restauranteDB"
}

variable "region" {
  description = "A região para o provisionamento dos recursos"
  type        = string
  default     = "us-east-1"
}

variable "vpc_id" {
  description = "ID da VPC"
  type        = string
}

variable "eks_cluster_cidr" {
  description = "CIDR do cluster EKS"
  type        = string
}