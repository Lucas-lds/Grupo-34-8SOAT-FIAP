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