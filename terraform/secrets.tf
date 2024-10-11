resource "kubernetes_secret" "db_secret" {
  metadata {
    name = "db-secret"
  }

  type = "Opaque"

  data = {
    mysql_database = "cmVzdGF1cmF0ZURC"            # base64 encoded value
    mysql_user     = "cmVzdGF1cmF0ZV91c2Vy"        # base64 encoded value
    mysql_password = "cmVzdGF1cmF0ZV91c2VyX3Bhc3M" # base64 encoded value
  }
}
