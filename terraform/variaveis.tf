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

variable "region" {
  description = "A região para o provisionamento dos recursos"
  type        = string
  default     = "us-east-1" # Valor padrão para a região
}

variable "gateway_url" {
  description = "The URL of the API Gateway."
  type        = string
  default = "https://example.execute-api.us-east-1.amazonaws.com"
}

variable "cognito_user_pool_id" {
  description = "The ID of the Cognito User Pool."
  type        = string
  default = "us-east-1_ABC123456"
}

variable "GATEWAY_URL" {
  description = "The URL of the API Gateway."
  type        = string
  default = "https://example.execute-api.us-east-1.amazonaws.com"
}

variable "COGNITO_USER_POOL_ID" {
  description = "The ID of the Cognito User Pool."
  type        = string
  default = "us-east-1_ABC123456"
}
