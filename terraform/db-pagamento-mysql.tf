### RDS SUBNET GROUP ### 
resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "my-rds-subnet-group"
  subnet_ids = ["subnet-0ba1d16a81898b46b", "subnet-02e5a2960cbaf6259", "subnet-057aa3ff60937097d"]

  tags = {
    Name = "My RDS Subnet Group"
  }
}

### RDS MySQL para o serviço de pagamento ###
resource "aws_db_instance" "mysql_rds_pagamento" {
  identifier          = "pagamento-mysql"
  engine              = "mysql"
  engine_version      = "8.0"
  instance_class      = "db.t3.micro"
  allocated_storage   = 20
  storage_type        = "gp2"
  username            = var.mysql_user_pagamento
  password            = var.mysql_password_pagamento
  db_name             = var.mysql_database_pagamento
  skip_final_snapshot = true

  db_subnet_group_name   = aws_db_subnet_group.rds_subnet_group.name
  vpc_security_group_ids = [aws_security_group.sg_pagamento.id]
}