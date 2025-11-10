# Ecommerce Iguarias Mineiras - Backend

Esse projeto compõe o backend de um ecommerce especializado em produtos típicos mineiros, como doce de leite, pé de moleque, queijos e muito mais.

Foi criado utilizando o Spring Initializr.

## Pré Requisitos

- Java JDK 17;
- Docker.

## Como Rodar

### Opção 1: Utilizando Docker somente para o banco (Linux)

- Em um terminal, navegue até o diretório raiz do projeto;
- Rode o comando `docker ps -a` para verificar se não já existe uma imagem do `java_db`;
- Caso já exista, não é necessário criar novamente com o comando `docker compose up`, se não houve nenhuma alteração. Porém, caso queira remover a imagem existente, rode `docker rm <nome_container_ou_id>`;
- Rode o comando `docker start java_db`, se a imagem existe, porém não está rodando;
- Caso não exista, rode o comando `docker compose up -d java_db`, para criar uma imagem docker de Postgres, conforme as configurações do arquivo `docker-compose.yml`;
- Configure as seguintes variaveis de ambiente para rodar a aplicação Java (na IDE ou pelo terminal):
    - DATABASE_URL=jdbc:postgresql://localhost:5433/postgres (Precisa ser na porta 5433, por conta do mapeamento feito em docker-compose.yml)
    - DATABASE_USERNAME=postgres
    - DATABASE_PASSWORD=postgres
    - PASSWORD_HASH_SECRET_KEY=(crie uma chave secreta segura)
- Rode a aplicação com o comando `./gradlew bootRun`, ou rode pela IDE.

## Opção 2: Utilizando Docker (Linux)

- Em um terminal, navegue até o diretório raiz do projeto;
- Rode o comando ./gradlew build para gerar o arquivo jar, que fica dentro do diretório `/build/libs`;
- Rode o comanod `docker compose build` para criar a imagem docker da aplicação spring boot;
- Rode o comando `docker compose up`.
