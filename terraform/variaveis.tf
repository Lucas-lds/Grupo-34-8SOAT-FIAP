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

variable "mysql_user_pagamento" {
  description = "MySQL User para o serviço de pagamento"
  type        = string
  default     = "pagamento_user"
}

variable "mysql_password_pagamento" {
  description = "MySQL Password para o serviço de pagamento"
  type        = string
  default     = "pagamento_user_pass"
}

variable "mysql_database_pagamento" {
  description = "MySQL Database para o serviço de pagamento"
  type        = string
  default     = "pagamento_db"
}

variable "mysql_user_produto" {
  description = "MySQL User para o serviço de produto"
  type        = string
  default     = "produto_user"
}

variable "mysql_password_produto" {
  description = "MySQL Password para o serviço de produto"
  type        = string
  default     = "produto_user_pass"
}

variable "mysql_database_produto" {
  description = "MySQL Database para o serviço de produto"
  type        = string
  default     = "produto_db"
}

variable "vpc_id" {
  description = "ID da VPC"
  type        = string
}

variable "eks_cluster_cidr" {
  description = "CIDR do cluster EKS"
  type        = string
}