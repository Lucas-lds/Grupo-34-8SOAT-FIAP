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
- Docke

## 📖 Documentação

Nessa solução estamos utilizando o Domain Driven Design (DDD) para entender e modelar o domínio do negócio, identificando e categorizando os subdomínios.

Como documentação, foram criados alguns artefatos para facilitar o entendimento da equipe, sendo eles:
- Domain Storytelling
- Requests (API)
  - Swagger: http://localhost/swagger
 
A solução adota o Domain Driven Design (DDD) para entender e estruturar o domínio do negócio, focando na identificação e categorização dos subdomínios.

Como parte da documentação, foram desenvolvidos diversos artefatos para apoiar a equipe, incluindo:
- Domain Storytelling
- Requests (API)
  - Swagger: http://localhost/swagger
 
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
http://localhost/swagger
