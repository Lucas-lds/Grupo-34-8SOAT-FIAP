# Secret para o banco de dados de produto
resource "kubernetes_secret" "db_produto_secret" {
  metadata {
    name      = "db-produto-secret"
    namespace = "default"
  }

  data = {
    mysql_user     = base64encode(var.mysql_user_produto)
    mysql_password = base64encode(var.mysql_password_produto)
    mysql_database = base64encode(var.mysql_database_produto)
  }
}