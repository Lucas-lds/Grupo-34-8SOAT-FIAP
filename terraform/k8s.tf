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
        service_account_name = kubernetes_service_account.db_monolito_mysql_service_account.metadata[0].name

        container {
          name  = "restaurante"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio:v8"

          env {
            name  = "RDS_ENDPOINT"
            value = aws_db_instance.mysql_rds.endpoint # Referência ao endpoint
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

### DEPLOYMENT PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_deployment" "pedido-api" {
  depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

  metadata {
    name = "pedido"
    labels = {
      app = "pedido"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "pedido"
      }
    }

    template {
      metadata {
        labels = {
          app = "pedido"
        }
      }

      spec {
        service_account_name = kubernetes_service_account.db_pedido_dynamodb_account.metadata[0].name

        container {
          name  = "pedido"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio-pedido:v1"

          env {
            name  = "DYNAMODB_ENDPOINT"
            value = "https://dynamodb.${var.region}.amazonaws.com"
          }
          env {
            name  = "DYNAMODB_TABLE_NAME"
            value = aws_dynamodb_table.pedido_table.name
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

### DEPLOYMENT PARA O SERVIÇO DE PAGAMENTO ###
resource "kubernetes_deployment" "pagamento-api" {
  depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

  metadata {
    name = "pagamento"
    labels = {
      app = "pagamento"
    }
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "pagamento"
      }
    }

    template {
      metadata {
        labels = {
          app = "pagamento"
        }
      }

      spec {
        service_account_name = kubernetes_service_account.db_pagamento_mysql_service_account.metadata[0].name

        container {
          name  = "pagamento"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio-pagamento:v1"

          env {
            name  = "RDS_ENDPOINT"
            value = aws_db_instance.mysql_rds_pagamento.endpoint # Referência ao endpoint
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

          env_from {
            secret_ref {
              name = kubernetes_secret.db_pagamento_secret.metadata[0].name
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

### DEPLOYMENT PARA O SERVIÇO DE produto ###
resource "kubernetes_deployment" "produto-api" {
  depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

  metadata {
    name = "produto"
    labels = {
      app = "produto"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "produto"
      }
    }

    template {
      metadata {
        labels = {
          app = "produto"
        }
      }

      spec {
        service_account_name = kubernetes_service_account.db_produto_mysql_service_account.metadata[0].name

        container {
          name  = "produto"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio-produto:v1"

          env {
            name  = "RDS_ENDPOINT"
            value = aws_db_instance.mysql_rds_produto.endpoint # Referência ao endpoint
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

          env_from {
            secret_ref {
              name = kubernetes_secret.db_produto_secret.metadata[0].name
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

### SERVICE PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_service" "pedido_service" {
  metadata {
    name = "pedido-service"
    labels = {
      app = "pedido"
    }
  }

  spec {
    selector = {
      app = "pedido"
    }

    port {
      port        = 80
      target_port = 8080
    }

    type = "ClusterIP"
  }
}

### HPA PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_horizontal_pod_autoscaler" "pedido_hpa" {
  metadata {
    name = "pedido-hpa"
  }

  spec {
    scale_target_ref {
      kind        = "Deployment"
      name        = kubernetes_deployment.pedido-api.metadata[0].name
      api_version = "apps/v1"
    }

    min_replicas = 1
    max_replicas = 10

    metrics {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 50
        }
      }
    }
  }
}

### SERVICE PARA O SERVIÇO DE PAGAMENTO ###
resource "kubernetes_service" "pagamento_service" {
  metadata {
    name = "pagamento-service"
    labels = {
      app = "pagamento"
    }
  }

  spec {
    selector = {
      app = "pagamento"
    }

    port {
      port        = 80
      target_port = 8080
    }

    type = "ClusterIP"
  }
}

### HPA PARA O SERVIÇO DE PAGAMENTO ###
resource "kubernetes_horizontal_pod_autoscaler" "pagamento_hpa" {
  metadata {
    name = "pagamento-hpa"
  }

  spec {
    scale_target_ref {
      kind        = "Deployment"
      name        = kubernetes_deployment.pagamento-api.metadata[0].name
      api_version = "apps/v1"
    }

    min_replicas = 1
    max_replicas = 10

    metrics {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 50
        }
      }
    }
  }
}

### SERVICE PARA O SERVIÇO DE produto ###
resource "kubernetes_service" "produto_service" {
  metadata {
    name = "produto-service"
    labels = {
      app = "produto"
    }
  }

  spec {
    selector = {
      app = "produto"
    }

    port {
      port        = 80
      target_port = 8080
    }

    type = "ClusterIP"
  }
}

### HPA PARA O SERVIÇO DE produto ###
resource "kubernetes_horizontal_pod_autoscaler" "produto_hpa" {
  metadata {
    name = "produto-hpa"
  }

  spec {
    scale_target_ref {
      kind        = "Deployment"
      name        = kubernetes_deployment.produto-api.metadata[0].name
      api_version = "apps/v1"
    }

    min_replicas = 1
    max_replicas = 10

    metrics {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 50
        }
      }
    }
  }
}

### OUTPUT PARA O SERVIÇO DE PEDIDO ###
output "pedido_service_url" {
  value = kubernetes_service.pedido_service.metadata[0].name
}

### OUTPUT PARA O SERVIÇO DE PAGAMENTO ###
output "pagamento_service_url" {
  value = kubernetes_service.pagamento_service.metadata[0].name
}

### OUTPUT PARA O SERVIÇO DE produto ###
output "produto_service_url" {
  value = kubernetes_service.produto_service.metadata[0].name
}