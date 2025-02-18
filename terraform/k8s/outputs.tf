### DNS NAME DATA SOURCE ###
output "URL" {
  value = length(kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress) > 0 ? kubernetes_service.restaurante-api_service.status[0].load_balancer[0].ingress[0].hostname : "Ingress não disponível"
}