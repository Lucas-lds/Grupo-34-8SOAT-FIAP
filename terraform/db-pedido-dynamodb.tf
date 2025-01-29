### DynamoDB para o serviço de pedidos ###
resource "aws_dynamodb_table" "pedido_table" {
  name           = "Pedidos"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "PedidoID"

  attribute {
    name = "PedidoID"
    type = "S"
  }

  tags = {
    Name = "Pedidos Table"
  }
}