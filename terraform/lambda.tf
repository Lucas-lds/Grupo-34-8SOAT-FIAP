resource "null_resource" "prepare_lambda_code" {
  provisioner "local-exec" {
    command = <<-EOT
      # Exibe o diretório atual para diagnóstico
      echo "Current directory:"
      pwd

      # Exibe o conteúdo dos diretórios relevantes
      echo "Listing contents of terraform directory:"
      ls -l

      # Cria o diretório de pacotes se não existir
      mkdir -p ./lambda_package

      # Instala a dependência cpf dentro do diretório lambda_package
      pip install cpf -t ./lambda_package

      # Cria o diretório lambda fora da pasta terraform, se não existir
      mkdir -p /home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda

      # Exibe o conteúdo do diretório lambda para garantir que exista
      echo "Listing contents of lambda directory before zipping:"
      ls -l /home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda

      # Garante permissões adequadas no diretório lambda
      chmod -R 755 /home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda

      # Cria o arquivo ZIP com o código e as dependências no diretório correto
      cd ./lambda_package && zip -r /home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip .
    EOT
  }

  # Garante que o código seja preparado antes de criar a Lambda
  triggers = {
    always_run = "${timestamp()}"
  }
}

resource "aws_lambda_function" "auth_function" {
  function_name    = "authFunction"
  role             = aws_iam_role.lambda_exec_role.arn
  handler          = "lambda_function.lambda_handler"
  runtime          = "python3.8"
  filename         = "/home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip"  # Caminho absoluto para o arquivo ZIP
  #source_code_hash = filebase64sha256("/home/runner/work/Grupo-34-8SOAT-FIAP/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip") # Calcula o hash do código

  environment {
    variables = {
      COGNITO_USER_POOL_ID = aws_cognito_user_pool.restaurante_user_pool.id # Referência ao Cognito User Pool ID
    }
  }

  timeout = 10 # Aumenta o timeout para 10 segundos

  # Garante que o código seja preparado antes de criar a Lambda
  depends_on = [null_resource.prepare_lambda_code]

  lifecycle {
    create_before_destroy = true
  }
}
