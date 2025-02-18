# Grupo-34-8SOAT-FIAP

<h1 align="center">FastFood API: Solução Completa de Auto-Atendimento para Restaurantes</h1>

<p align="center">
  <a href="#Sobre">Sobre</a>&nbsp;&nbsp;|&nbsp;&nbsp;
  <a href="#tecnologias">Tecnologias</a>&nbsp;&nbsp;|&nbsp;&nbsp;
  <a href="#documentação">Documentação</a>&nbsp;&nbsp;|&nbsp;&nbsp;
  <a href="#instalação">Instalação</a>
</p>

<p align="center">
  <a href="#-license">
    <img alt="License" src="https://img.shields.io/static/v1?label=license&message=MIT&color=24d67d&labelColor=000000">
  </a>
</p>

## 📃 Sobre

O FastFood API oferece uma solução inovadora de auto-atendimento para restaurantes, gerenciando todo o fluxo de pedidos desde a criação, passando pelo pagamento e preparo, até o monitoramento e entrega ao cliente.

Este projeto faz parte do tech challenge da pós-graduação em Arquitetura de Software da FIAP. Nos próximos meses, a solução passará por uma série de evoluções graduais. Portanto, embora ainda não tenha todas as funcionalidades planejadas, estamos trabalhando para aprimorá-la continuamente. 

## Changelog Fase II:
- **Refatoração do projeto**: Refatorando o código para seguir os padrões clean code e clean architecture;
- **Refatoração do sistema de infraestrutura** Utilizo o Terraform para provisionar a infraestrutura na AWS e o EKS para criar o cluster. Com o provedor do Kubernetes, gerencio o deployment, o HPA e o service;
-  **Elastic Constainer Registry**: O repositório de containers da AWS, onde vamos colocar as nossas imagens.
- **Migração do banco de dados da API** Para o AWS RDS com a engine MySQL, visando maior escalabilidade e segurança;
- **APIs Implementadas**:
  - **Checkout de Pedido**: Agora recebe os produtos solicitados e retorna a identificação do pedido.
  - **Consulta de Status de Pagamento**: API que informa se o pagamento do pedido foi aprovado.
  - **Webhook de Confirmação de Pagamento**: Recebe confirmações de pagamentos aprovados ou recusados.
  - **Listagem de Pedidos**: Retorna pedidos com suas descrições, ordenados por:
     - **Status**:  Pronto > Em Preparação > Recebido.
     - **Antiguidade**:  Pedidos mais antigos primeiro.
     - **Exclusão**: Pedidos com status Finalizado não aparecem.

## Changelog - Fase III
### 1. Implementação do API Gateway, Lambda e Cognito para Autenticação de Cliente
- **API Gateway**: Configurado o **API Gateway** para gerenciar as requisições HTTP e direcioná-las para funções específicas.
- **Autenticação com CPF**: Implementada uma função serverless utilizando **AWS Lambda** que valida o **CPF** do cliente.
- **Amazon Cognito**: A função **Lambda** agora verifica se o **CPF** fornecido está registrado no **User Pool** do **Amazon Cognito**, realizando a autenticação do cliente.

### 2. Integração com GitHub Actions (CI/CD)
- **Pipeline GitHub Actions**: Adicionada uma **pipeline de CI/CD** utilizando o **GitHub Actions** para automatizar o processo de build, testes e deploy do projeto.

### 3. Segregação de Recursos Terraform (IaC)
- **Segregação de Recursos Terraform**: Segregados os arquivos de configuração, como **eks.tf**, **rds.tf**, entre outros, em um único repositório chamado **terraform**, seguindo as melhores práticas de **CI/CD** para manter a infraestrutura como código organizada, modular e reutilizável.

### 4. Melhoria na Estrutura do Banco de Dados com Amazon RDS
- **Amazon RDS (MySQL)**: Melhorada a estrutura do banco de dados ao migrar para **Amazon RDS** com a engine **MySQL**, garantindo maior escalabilidade, segurança e facilidade no gerenciamento do banco de dados.

## Changelog - Fase IV

### 1. Refatoração do Projeto para Microsserviços
- **Microsserviço de Pedido**: Criado o microsserviço **Pedido**, responsável por registrar e listar pedidos, com banco de dados **DynamoDB** (NoSQL).
- **Microsserviço de Pagamento**: Criado o microsserviço **Pagamento**, responsável por gerenciar a cobrança e atualizar o status do pedido, utilizando **RDS/MySQL** (SQL).
- **Microsserviço de Produção**: Criado o microsserviço **Produção**, que gerencia a fila de pedidos e atualiza o status de cada etapa do processo de produção, com banco de dados **RDS/MySQL** (SQL).

### 2. Banco de Dados Separado para Cada Microsserviço
- **DynamoDB (NoSQL)**: O microsserviço **Pedido** foi integrado ao **DynamoDB**, garantindo alta performance e escalabilidade.
- **RDS/MySQL (SQL)**: Os microsserviços **Pagamento** e **Produção** foram configurados para utilizar **RDS/MySQL**, proporcionando uma estrutura relacional e facilitando a gestão de dados complexos.

### 3. Manutenção dos Módulos Restantes no Monolito
- **Monolito**: Os demais módulos do sistema permanecem no formato monolítico, com a migração para microsserviços planejada para fases futuras.

### 4. Implementação de Testes Unitários
- **Testes Unitários**: Todos os microsserviços agora contêm testes unitários para garantir a confiabilidade das funcionalidades.
- **BDD**: Pelo menos um caminho de teste foi implementado utilizando **BDD** (Behavior-Driven Development).
- **Cobertura de Testes**: A cobertura de testes foi estabelecida em **70%** ou mais em todos os microsserviços.

### 5. Organização dos Repositórios e CI/CD
- **Repositórios Separados**: Cada microsserviço agora possui seu próprio repositório, seguindo a abordagem de repositórios separados para cada aplicação. Os **links** para os repositórios dos microsserviços e do monolito são:
  - [Monolito (Grupo-34-8SOAT-FIAP)](https://github.com/Lucas-lds/Grupo-34-8SOAT-FIAP)
  - [Microsserviço de Pedido](https://github.com/Lucas-lds/grupo-34-restaurante-pedido)
  - [Microsserviço de Produto](https://github.com/Lucas-lds/grupo-34-restaurante-produto)
  - [Microsserviço de Pagamento](https://github.com/Lucas-lds/grupo-34-restaurante-pagamento)
  
- **Proteção da Branch Main/Master**: As branches **main/master** estão protegidas, impedindo commits diretos.
- **Pull Requests e Validação de Build**: Pull requests para a branch **main/master** agora validam o build da aplicação, e a qualidade do código é verificada utilizando **SonarQube**, com cobertura de código mínima de **70%**.
- **CI/CD Integrado**: Todos os microsserviços estão configurados com **CI/CD** e, no merge de pull requests, o deploy de todos os microsserviços é executado corretamente.

### 6. Cobertura das Classes de Teste:
![image](https://github.com/user-attachments/assets/dca17af4-5d02-41b4-95ae-3e8563c2b5a8)

## 🚀 Tecnologias

Tecnologias e Arquitetura do Projeto
As tecnologias selecionadas para este projeto foram escolhidas com a finalidade de garantir uma solução escalável, resiliente e fácil de manter. Para planejar os próximos passos, optou-se por uma arquitetura monolítica modular, a qual facilita a futura migração para uma abordagem baseada em microsserviços.

Durante o processo de documentação do DDD, os contextos delimitados foram transformados em módulos dentro da solução. Esses módulos podem ser extraídos de maneira eficiente para formar microsserviços quando necessário.

## Stack utilizada:

- Domain Driven Design (DDD)
- Arquitetura Hexagonal
- Clean Architecture
- Clean Code 
- Java
- MySQL
- Docker
- Kubernetes
- Desenho Arquitetura - AWS
  - RDS
  - DYNAMODB
  - ECR
  - EKS
  - COGNITO
  - API-GATEWAY
  - LAMBDA

## 📖 Documentação
 
A solução adota o Domain Driven Design (DDD) para entender e estruturar o domínio do negócio, focando na identificação e categorização dos subdomínios.

Como parte da documentação, foram desenvolvidos diversos artefatos para apoiar a equipe, incluindo:
- [Glossário da Linguagem Ubíqua](/documents/linguagem-ubiqua/glossario.md)
- Domain Storytelling
  - [Fluxo de pedido](/docs/storytelling/01-FastFood-FluxoDePedido.png)
  - [Fluxo de pedido - Pagamento recusado](/docs/storytelling/02-FastFood-FluxoDePedidoPagamentoRecusado.png)
  - [Fluxo de preparo](/docs/storytelling/03-FastFood-FluxoDePreparo.png)
- [Event Storming](https://miro.com/app/board/uXjVKFvfVYM=/)  
- Requests (API)
  - Swagger: http://localhost:8080/api/v1/swagger-ui/index.html#/
- [AWS](/documents/AWS/diagrama-aws-fase-4.png)
- [justificativa-mysql] (/documents/justificativa-mysql/justificativa-mysql.png)
- [Vídeo](https://youtu.be/JzOYcsCBB5M)
- [Collection](https://api.postman.com/collections/9276431-267e5c70-e0e6-455d-8de2-472a2862f7b2?access_key=PMAT-01J9YZDJ12YND5V2FCYB0HPG31)
 
## 💻 Instalação

**Premissas:**
- Ter o docker instalado na maquina.

Para executar a aplicação siga o passo a passo a seguir.

Primeiro, clone o repositório para a sua máquina local:

```bash
git clone git@github.com:Lucas-lds/Grupo-34-8SOAT-FIAP.git
```

Construa as imagens e inicie os serviços com o Docker Compose:

```bash
docker compose up --build -d
```

Para parar os serviços e remover os contêineres, execute:

```bash
docker compose down
```

Acessar no navegador:
http://localhost:8080/api/v1/swagger-ui/index.html#/

## Changelog da Instalação Fase II:

**Premissas:**

 - Ter o Terraform instalado na máquina.
 - Possuir uma conta na AWS.

 **Passo a Passo para Configuração:**

 1. Criar uma Conta na AWS:
  - Acesse: https://aws.amazon.com/
  - Clique em "Criar uma Conta da AWS".
  - Siga as instruções para registrar-se, incluindo o fornecimento de informações de     pagamento (um cartão de crédito é necessário, mas você pode utilizar o nível gratuito).

 2. Criar um Usuário IAM:
  - Acesse o Console AWS: AWS Management Console
  - Navegue até IAM:
    - No console, procure por "IAM" (Identity and Access Management).
  - Criar Usuário:
    - Clique em "Users" e depois em "Add user".
  - Definir Permissões:
    - Escolha "Attach existing policies directly" e selecione políticas como AdministratorAccess (ou crie uma política personalizada conforme necessário).
  - Finalizar Criação:
    - Clique em "Create user" e anote a Access Key ID e Secret Access Key geradas. Guarde essas informações em um local seguro!

 3. Instalar e Configurar o AWS CLI:
  - Instalação do AWS CLI: 
    - Siga as instruções de instalação para seu sistema operacional. Veja as https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html
  - Configurar o AWS CLI:
    - Abra seu terminal ou prompt de comando.
    - Execute o comando:
    ```bash
    aws configure
    ```
    - Quando solicitado, insira:
    - **AWS Access Key ID:** (sua Access Key ID)
    - **AWS Secret Access Key:** (sua Secret Access Key)
    - **Default region name:** (exemplo: us-east-1)
    - **Default output format:** (opcional, como json)

  4. Testar a Configuração:
    - Para verificar se tudo está funcionando, execute:
    ```bash
    aws s3 ls
    ```      
    - Se estiver tudo configurado corretamente, você verá uma lista de seus buckets S3 (ou uma mensagem indicando que você não tem nenhum).

  **Executando a Aplicação**  

  1. Criar o ECR (Elastic Container Registry)
  - Passo 1: Acesse a interface da AWS.
  - Passo 2: Navegue até o serviço ECR (Elastic Container Registry).
  - Passo 3: Crie um repositório com o nome repositorio.
    - **Observação: É necessário criar o repositório antes de fazer o push da imagem.**

  2. Preparar e Enviar a Imagem Docker
  - Passo 1: Acesse o diretório /docker onde se encontra o script para construção da imagem.
    ```bash
    cd /docker
    ``` 
  - Passo 2: Abra o script e preencha as seguintes variáveis com suas informações:
    - **AWS_REGION=""** (ex: us-east-1) 
    - **ACCOUNT_ID=""** (seu ID da conta AWS)  
    - **REPO_NAME=""** (nome do repositório, neste caso repositorio)  
    - **IMAGE_TAG=""** (tag desejada para a imagem, ex: latest)  
  - Passo 3: Execute o script para:
    - Construir a imagem
    - Taguear a imagem
    - Logar no ECR
    - Fazer o push da imagem para o ECR 

  3. Gerenciar Infraestrutura com Terraform
  - Passo 1: Navegue até o diretório /terraform utilizando o terminal:
    ```bash
    cd /terraform
    ``` 
  - Passo 2: Execute o comando para verificar a infraestrutura que será criada:
    ```bash
    terraform plan
    ```     
  - Passo 3: Para criar a infraestrutura, execute:
    ```bash
    terraform apply --auto-approve   
    ```   
  - Passo 4: Após a finalização do provisionamento de toda a infraestrutura na AWS, o terminal exibirá a URL da aplicação.
    - Para acessar a interface da aplicação, adicione o endpoint **/api/v1/swagger-ui/** à URL e cole no navegador. 
    -**Exemplo:** a8ee83e4bc22a4019af48ebfa6656574-1293916010.us-east-1.elb.amazonaws.com/api/v1/swagger-ui/index.html   
  - Passo 5: Para excluir toda a infraestrutura na AWS, execute:
    ```bash
    terraform destroy --auto-approve
    ```   
