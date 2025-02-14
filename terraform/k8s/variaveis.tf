variable "cluster_name" {
  type    = string
  default = "restaurante-cluster2"
}

variable "region" {
  description = "A região para o provisionamento dos recursos"
  type        = string
  default     = "us-east-1"
}
