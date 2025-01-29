# Recurso para criar uma instância EC2 que funcionará como um bastion
# para acessar um banco de dados RDS em uma sub-rede privada
resource "aws_instance" "db_client_produto" {
  ami                    = "ami-0ebfd941bbafe70c6"
  instance_type          = "t2.micro"
  key_name               = "chave-fiap2"
  vpc_security_group_ids = [aws_security_group.ssh_cluster.id]
  subnet_id              = "subnet-0ba1d16a81898b46b"

  # Copia o arquivo SQL para a instância
  provisioner "file" {
    source      = "../docker/init-produto.sql"
    destination = "/home/ec2-user/init-produto.sql"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = tls_private_key.tls_private.private_key_pem
      host        = self.public_ip
    }
  }

  provisioner "remote-exec" {
    inline = [
      "sudo yum update -y", # Instala o cliente MySQL, se necessário
      "sudo wget https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm",
      "sudo dnf install mysql80-community-release-el9-1.noarch.rpm -y",
      "sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023",
      "sudo yum install -y mysql-community-client",
      "mysql -h ${aws_db_instance.mysql_rds_produto.address} -u ${var.mysql_user_produto} -p${var.mysql_password_produto} -e 'CREATE DATABASE IF NOT EXISTS ${var.mysql_database_produto};'",
      "mysql -h ${aws_db_instance.mysql_rds_produto.address} -u ${var.mysql_user_produto} -p${var.mysql_password_produto} ${var.mysql_database_produto} < /home/ec2-user/init-produto.sql" # Caminho do arquivo SQL na instância
    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = tls_private_key.tls_private.private_key_pem # Caminho para sua chave privada
      host        = self.public_ip                              # Se a instância for pública
    }
  }

  depends_on = [aws_db_instance.mysql_rds_produto, aws_key_pair.generated_key]
}