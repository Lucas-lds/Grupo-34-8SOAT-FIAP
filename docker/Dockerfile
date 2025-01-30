# Etapa de build.
FROM maven:3.8.5-openjdk-17 AS build

# Diretório de trabalho.
WORKDIR /app

# Copiar apenas o arquivo pom.xml e baixar as dependências.
COPY pom.xml .

## Executar o comando para baixar as dependências do Maven e prepará-las para um build offline
RUN mvn dependency:go-offline -B

# Copiar o restante dos arquivos de código-fonte.
COPY src ./src

# Construir a aplicação.
RUN mvn clean package -DskipTests
# A flag '-DskipTests' evita a execução de testes durante o build para acelerar o processo.



# Etapa de runtime
FROM openjdk:17-slim AS runtime
# Define a imagem base para a etapa de runtime com OpenJDK 17 em uma versão slim.
# A versão slim é menor e mais eficiente para executar a aplicação.

WORKDIR /app

# Instalar jq e AWS CLI
RUN apt-get update && apt-get install -y \
    jq \
    curl \
    unzip \
    && curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install \
    && rm -rf awscliv2.zip ./aws

# Copiar o arquivo JAR construido da etapa de build para o diretório de trabalho.
COPY --from=build /app/target/restaurante-0.0.1-SNAPSHOT.jar ./app.jar

# Copiar o script init.sh para dentro da imagem
COPY docker/init.sh /app/init.sh

# Garantir que o script tenha permissão de execução
RUN chmod +x /app/init.sh

# Definir argumentos de build para as chaves de acesso AWS
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

# Exemplo com credenciais (não recomendado para produto)
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV AWS_DEFAULT_REGION=us-east-1

# Definir o ponto de entrada para o contêiner: executar o script init.sh
ENTRYPOINT ["/app/init.sh"]

EXPOSE  8080 

# Comando para executar a aplicação.
#ENTRYPOINT [ "java", "-jar", "app.jar" ]
