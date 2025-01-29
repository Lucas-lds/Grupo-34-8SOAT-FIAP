# Secret para o banco de dados de pagamento
resource "kubernetes_secret" "db_pagamento_secret" {
  metadata {
    name      = "db-pagamento-secret"
    namespace = "default"
  }

  data = {
    mysql_user     = base64encode(var.mysql_user_pagamento)
    mysql_password = base64encode(var.mysql_password_pagamento)
    mysql_database = base64encode(var.mysql_database_pagamento)
  }
}