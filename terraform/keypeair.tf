# Recurso para gerar uma chave privada TLS RSA
resource "tls_private_key" "tls_private" {
  algorithm   = "RSA"
  ecdsa_curve = "P384"
}

# Recurso para criar um par de chaves na AWS
resource "aws_key_pair" "generated_key" {
  key_name   = "chave-fiap2"                                  # Nome da chave que será utilizada para acessar a instância EC2
  public_key = tls_private_key.tls_private.public_key_openssh # Chave pública gerada a partir da chave privada
}

# Saída que exibe a chave privada em formato PEM
output "pem" {
  value     = tls_private_key.tls_private.private_key_pem # Valor da chave privada
  sensitive = true                                        # Marca a saída como sensível, não será exibida em logs
}

# Recurso para criar um segredo no AWS Secrets Manager
resource "aws_secretsmanager_secret" "monolito" {
  name = "keypair-v7" # Nome do segredo que será armazenado
}

# Recurso para armazenar a versão do segredo no AWS Secrets Manager
resource "aws_secretsmanager_secret_version" "monolito" {
  secret_id = aws_secretsmanager_secret.monolito.id # ID do segredo que estamos atualizando
  secret_string = jsonencode({

    "KeyName" : "${aws_key_pair.generated_key.key_name}",           # Nome da chave gerada
    "RSAPUB" : "${tls_private_key.tls_private.public_key_openssh}", # Chave pública em formato OpenSSH
    "PEM" : "${tls_private_key.tls_private.private_key_pem}"        # Chave privada em formato PEM
  })
}
