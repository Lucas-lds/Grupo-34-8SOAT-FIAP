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

      # Garante que o diretório lambda exista, usando caminho absoluto
      mkdir -p /home/raf/Desktop/pos-fiap/fase2/Grupo-34-8SOAT-FIAP/lambda

      # Exibe o conteúdo do diretório lambda para garantir que exista
      echo "Listing contents of lambda directory:"
      ls -l /home/raf/Desktop/pos-fiap/fase2/Grupo-34-8SOAT-FIAP/lambda

      # Cria o arquivo ZIP com o código e as dependências
      cd ./lambda_package && zip -r /home/raf/Desktop/pos-fiap/fase2/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip .
    EOT
  }

  # Garante que o código seja preparado antes de criar a Lambda
  triggers = {
    always_run = "${timestamp()}"
  }
}

resource "aws_lambda_function" "auth_function" {
  function_name = "authFunction"
  role          = aws_iam_role.lambda_exec_role.arn
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.8"
  filename      = "/home/raf/Desktop/pos-fiap/fase2/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip" # Caminho absoluto do arquivo ZIP
  #source_code_hash = filebase64sha256("/home/raf/Desktop/pos-fiap/fase2/Grupo-34-8SOAT-FIAP/lambda/lambda_function.zip") # Calcula o hash do código

  environment {
    variables = {
      COGNITO_USER_POOL_ID = aws_cognito_user_pool.restaurante_user_pool.id         # Referência ao Cognito User Pool ID
      COGNITO_CLIENT_ID    = aws_cognito_user_pool_client.restaurante_app_client.id # Referência ao Cognito Client ID
    }
  }

  timeout = 10 # Aumenta o timeout para 10 segundos

  # Garante que o código seja preparado antes de criar a Lambda
  depends_on = [null_resource.prepare_lambda_code]


  lifecycle {
    create_before_destroy = true
  }
}