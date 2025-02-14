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