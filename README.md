# Backend

Esse projeto é o backend do Ecommerce Iguarias Mineiras.

Foi criado utilizando o Spring Initializr.

## Pré Requisitos

- Docker

## Como Rodar usando Docker somente para banco (Linux)

- Em um terminal, navegue até o diretório raiz do projeto;
- Rode o comando `docker ps -a` para verificar se não já existe uma imagem do `java_db` rodando;
- Caso já exista, não é necessário rodar novamente o `docker compose up`, porém, caso queira remover a imagem existente, rode `docker rm <nome_container_ou_id>`;
- Rode o comando `docker compose up -d java_db`, para rodar uma imagem docker de Postgres;
- Configure as seguintes variaveis de ambiente (na IDE ou pelo terminal):
  - DATABASE_URL=jdbc:postgresql://localhost:5433/postgres (Precisa ser na porta 5433, por conta do mapeamento feito em docker-compose.yml)
  - DATABASE_USERNAME=postgres
  - DATABASE_PASSWORD=postgres
  - PASSWORD_HASH_SECRET_KEY
- Rode a aplicação com o comando `./gradlew bootRun`, ou rode pela IDE

## Como Rodar em Docker (Linux)

- Em um terminal, navegue até o diretório raiz do projeto;
- Rode o comando ./gradlew build para gerar o arquivo jar, que fica dentro do diretório `/build/libs`;
- Rode o comanod `docker compose build` para criar a imagem docker da aplicação spring boot;
- Rode o comando `docker compose up`;
