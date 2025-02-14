# Buscar o output do RDS diretamente do diretório de infraestrutura
data "terraform_remote_state" "infra" {
  backend = "s3"
  config = {
    bucket = "terraform-state-restaurante"
    key    = "terraform.tfstate"
    region = "us-east-1"
  }
}


# Buscar o segredo Kubernetes existente
data "kubernetes_secret" "db_secret" {
  metadata {
    name      = "db-secret"
  }
}

# Buscar o Service Account existente
data "kubernetes_service_account" "rds_service_account" {
  metadata {
    name      = "rds-service-account"
    namespace = "default"
  }
}

### DEPLOYMENT ###
resource "kubernetes_deployment" "restaurante-api" {

  #depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

  metadata {
    name = "restaurante"
    labels = {
      app = "restaurante"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "restaurante"
      }
    }

    template {
      metadata {
        labels = {
          app = "restaurante"
        }
      }

      spec {
        service_account_name = data.kubernetes_service_account.rds_service_account.metadata[0].name

        container {
          name  = "restaurante"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio:v14"

          env {
            name = "MYSQL_DATABASE"
            value_from {
              secret_key_ref {
                name = data.kubernetes_secret.db_secret.metadata[0].name
                key  = "mysql_database"
              }
            }
          }

          env {
            name = "MYSQL_USER"
            value_from {
              secret_key_ref {
                name = data.kubernetes_secret.db_secret.metadata[0].name
                key  = "mysql_user"
              }
            }
          }

          env {
            name = "MYSQL_PASSWORD"
            value_from {
              secret_key_ref {
                name = data.kubernetes_secret.db_secret.metadata[0].name
                key  = "mysql_password"
              }
            }
          }                 

          #env {
            #name  = "RDS_ENDPOINT"
            #value = aws_db_instance.mysql_rds.endpoint # Referência ao endpoint
          #}

          #env {
            #name  = "RDS_ENDPOINT"
            #value = output.mysql_rds_endpoint # Referência ao endpoint
          #}

          env {
            name  = "RDS_ENDPOINT"
            value = data.terraform_remote_state.infra.outputs.mysql_rds_endpoint  # Usando o output do RDS
          }

          resources {
            limits = {
              cpu    = "1"
              memory = "1Gi"
            }
            requests = {
              cpu    = "500m"
              memory = "250Mi"
            }
          }

          port {
            container_port = 8080
          }

          #env_from {
            #secret_ref {
              #name = kubernetes_secret.db_secret.metadata[0].name
            #}
          #}

          liveness_probe {
            http_get {
              path = "/api/v1/actuator/health"
              port = 8080
            }

            initial_delay_seconds = 120
            period_seconds        = 10
            timeout_seconds       = 5
            failure_threshold     = 3
          }

        }
      }
    }
  }
}







