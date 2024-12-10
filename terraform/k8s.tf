### DEPLOYMENT ###
resource "kubernetes_deployment" "restaurante-api" {

  depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

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
        service_account_name = kubernetes_service_account.rds_service_account.metadata[0].name

        container {
          name  = "restaurante"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio:v5"

          env {
            name  = "RDS_ENDPOINT"
            value = aws_db_instance.mysql_rds.endpoint # Referência ao endpoint
          }

          resources {
            limits = {
              cpu    = "1"
              memory = "1G1"
            }
            requests = {
              cpu    = "500m"
              memory = "250Mi"
            }
          }

          port {
            container_port = 8080
          }

          env_from {
            secret_ref {
              name = kubernetes_secret.db_secret.metadata[0].name
            }
          }

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

### SERVICE ###
resource "kubernetes_service" "restaurante-api_service" {
  metadata {
    name = "restaurante-service"
  }

  spec {
    type = "LoadBalancer"

    selector = {
      app = "restaurante"
    }

    port {
      protocol    = "TCP"
      port        = 80
      target_port = 8080
    }
  }

  depends_on = [kubernetes_deployment.restaurante-api]
}

### DNS NAME DATA SOURCE ###
output "URL" {
  value = length(kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress) > 0 ? kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress[0].hostname : "Ingress não disponível"
}

### HORIZONTAL POD AUTOSCALER ###
resource "kubernetes_horizontal_pod_autoscaler" "restaurante-api_hpa" {
  metadata {
    name = "restaurante-hpa"
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.restaurante-api.metadata[0].name
    }

    min_replicas                      = 2
    max_replicas                      = 4
    target_cpu_utilization_percentage = 70
  }
}



