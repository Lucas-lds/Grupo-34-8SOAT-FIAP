resource "kubernetes_secret" "db_secret" {
  metadata {
    name = "db-secret"
  }

  type = "Opaque"

  data = {
    mysql_database = "cmVzdGF1cmFudGVEQg=="         # base64 encoded value
    mysql_user     = "cmVzdGF1cmFudGVfdXNlcg=="     # base64 encoded value
    mysql_password = "cmVzdGF1cmFudGVfdXNlcl9wYXNz" # base64 encoded value
  }
}
