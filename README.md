# Grupo-34-8SOAT-FIAP

<h1 align="center">FastFood API: Solução Completa de Auto-Atendimento para Restaurantes</h1>

<p align="center">
  <a href="#sobre">Sobre</a>&nbsp;&nbsp;|&nbsp;&nbsp;
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

## 🚀 Tecnologias

Tecnologias e Arquitetura do Projeto
As tecnologias selecionadas para este projeto foram escolhidas com a finalidade de garantir uma solução escalável, resiliente e fácil de manter. Para planejar os próximos passos, optou-se por uma arquitetura monolítica modular, a qual facilita a futura migração para uma abordagem baseada em microsserviços.

Durante o processo de documentação do DDD, os contextos delimitados foram transformados em módulos dentro da solução. Esses módulos podem ser extraídos de maneira eficiente para formar microsserviços quando necessário.

As principais tecnologias e conceitos empregados no projeto incluem:

- Domain Driven Design (DDD)
- Arquitetura Hexagonal
- Java
- MySQL
- Docker

## 📖 Documentação
 
A solução adota o Domain Driven Design (DDD) para entender e estruturar o domínio do negócio, focando na identificação e categorização dos subdomínios.

Como parte da documentação, foram desenvolvidos diversos artefatos para apoiar a equipe, incluindo:
- [Glossário da Linguagem Ubíqua](/documents/linguagem-ubiqua/glossario.md)
- Domain Storytelling
  - [Fluxo de pedido](/documents/storytelling/01-FastFood-FluxoDePedido.png)
  - [Fluxo de pedido - Pagamento recusado](/documents/storytelling/02-FastFood-FluxoDePedidoPagamentoRecusado.png)
  - [Fluxo de preparo](/documents/storytelling/03-FastFood-FluxoDePreparo.png)
- [Event Storming](https://miro.com/app/board/uXjVKFvfVYM=/)  
- Requests (API)
  - Swagger: http://localhost:8080/api/v1/swagger-ui/index.html
 
## 💻 Instalação

**Premissas:**
- Ter o docker instalado na máquina.

Para executar a aplicação siga o passo a passo a seguir.

Primeiro, clone o repositório para a sua máquina local:

```bash
git clone git@github.com:Lucas-lds/Grupo-34-8SOAT-FIAP.git
```

Navegue até o diretório onde o arquivo docker-compose.yml está localizado:

```bash
cd /caminho/para/o/diretorio/correto/docker
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
http://localhost:8080/api/v1/swagger-ui/index.html
