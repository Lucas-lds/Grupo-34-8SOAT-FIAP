resource "null_resource" "init_dynamodb" {
  provisioner "local-exec" {
    command = "python3 init_dynamodb.py"
  }

  depends_on = [aws_dynamodb_table.pedido_table]
}