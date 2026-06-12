# Source Code

Este diretório contém todos os projetos necessários para execução da aplicação.

## Estrutura

### [LeilaSalaoApi](./LeilaSalaoApi)

Projeto responsável pela API da aplicação.

Contém toda a regra de negócio, autenticação, gerenciamento de clientes, serviços, agendamentos e histórico de alterações.

### [LeilaSalaoFrontEnd](./LeilaSalaoFrontEnd)

Projeto responsável pela interface web da aplicação.

Desenvolvido em Angular, contém as telas administrativas e de clientes para gerenciamento dos agendamentos, serviços e usuários.

## Pré-requisitos

Para executar o projeto é necessário ter instalado:

- Docker
- Docker Compose

## Configuração

Antes de iniciar a aplicação, configure as variáveis de ambiente no arquivo (está disponivel no github de forma acadêmica):

```bash
.env
```

## Executando o projeto

Na raiz da pasta `source-code`, execute:

```bash
docker compose up -d
```

O Docker irá criar e iniciar todos os containers necessários para a aplicação.

## Verificando os containers

Para listar os containers em execução:

```bash
docker ps
```

## Parando a aplicação

Para interromper os containers:

```bash
docker compose down
```

## Acesso à aplicação

Após a inicialização dos containers, a aplicação estará disponível em:

```text
http://localhost:4200/
```

## Credenciais de Administrador

Utilize as seguintes credenciais para acessar a área administrativa:

```text
Email: admin@admin.com
Senha: admin
```

## Observações

Após a inicialização dos containers, a API e o Front-End estarão disponíveis conforme as portas configuradas no arquivo `docker-compose.yaml`.