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
  default     = "restaurante_user" # Valor padrão
}

variable "mysql_password" {
  description = "MySQL Password"
  type        = string
  default     = "restaurante_user_pass" # Valor padrão
}

variable "mysql_database" {
  description = "MySQL Database"
  type        = string
  default     = "restauranteDB" # Valor padrão
}
