# Leila Salão API

API REST desenvolvida para gerenciamento de um salão de beleza, permitindo o cadastro de clientes, serviços, agendamentos, autenticação via JWT e acompanhamento do histórico de alterações.

## Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot 4
* Spring MVC
* Spring Security
* OAuth2 Resource Server
* Spring Data JPA
* Jakarta Validation
* Maven

### Banco de Dados

* PostgreSQL (produção)
* H2 Database (desenvolvimento e testes)

### Segurança

* JWT (JSON Web Token)
* Controle de acesso baseado em Roles:

    * `admin`
    * `client`

### Testes

* Spring Security Test
* Spring MVC Test
* Spring Validation Test
* Spring Data JPA Test

---

# Arquitetura

O projeto segue uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Principais responsabilidades:

* Controllers: exposição dos endpoints REST.
* Services: regras de negócio.
* Repositories: acesso aos dados.
* DTOs: comunicação entre API e cliente.
* Security: autenticação e autorização.

---

# Autenticação

A autenticação é realizada através de JWT.

---

## Endpoints

| Método    | Endpoint                  | Descrição                                            | Permissão           |
|-----------|---------------------------|------------------------------------------------------|---------------------|
| POST      | `/auth`                   | Realiza autenticação e gera o token JWT              | Público             |
| POST      | `/clients`                | Cadastra um novo cliente                             | Público             |
| GET       | `/clients`                | Lista todos os clientes                              | Admin               |
| DELETE    | `/clients/{id}`           | Remove um cliente                                    | Admin               |
| POST      | `/services`               | Cadastra um novo serviço                             | Admin               |
| GET       | `/services`               | Lista os serviços disponíveis                        | Admin / Cliente     |
| DELETE    | `/services/{id}`          | Remove um serviço                                    | Admin               |
| POST      | `/schedulings`            | Cria um novo agendamento                             | Admin / Cliente     |
| GET       | `/schedulings`            | Lista todos os agendamentos                          | Admin               |
| GET       | `/schedulings/me`         | Lista os agendamentos do cliente autenticado         | Cliente             |
| GET       | `/schedulings/dates`      | Busca agendamentos por período                       | Público             |
| PUT       | `/schedulings/{id}`       | Atualiza um agendamento pelo cliente                 | Cliente             |
| PUT       | `/schedulings/admin/{id}` | Atualiza um agendamento pelo administrador           | Admin               |
| DELETE    | `/schedulings/{id}`       | Remove um agendamento                                | Admin               |
| GET       | `/dashboard`              | Retorna indicadores e métricas do sistema            | Admin               |
| GET       | `/history-changes/{id}`   | Consulta o histórico de alterações de um agendamento | Admin               |

---

# Controle de Acesso

| Endpoint                    | Admin | Cliente |
|-----------------------------|------|---------|
| POST /auth                  | ✅    | ✅       |
| POST /clients               | ✅    | ✅       |
| GET /clients                | ✅    | ❌       |
| DELETE /clients/{id}        | ✅    | ❌       |
| POST /services              | ✅    | ❌       |
| GET /services               | ✅    | ✅       |
| DELETE /services/{id}       | ✅    | ❌       |
| POST /schedulings           | ✅    | ✅       |
| GET /schedulings            | ✅    | ❌       |
| GET /schedulings/me         | ❌    | ✅       |
| PUT /schedulings/{id}       | ❌    | ✅       |
| PUT /schedulings/admin/{id} | ✅    | ❌       |
| DELETE /schedulings/{id}    | ✅    | ❌       |
| GET /dashboard              | ✅    | ❌       |
| GET /history-changes/{id}   | ✅    | ❌       |

---

# Executando o Projeto

## Clonar repositório

```bash
git clone https://github.com/seu-usuario/leila-salao-api.git
```

## Executar aplicação

```bash
mvn spring-boot:run
```

## Gerar build

```bash
mvn clean package
```

---

# Funcionalidades

* Cadastro de clientes
* Cadastro de serviços
* Controle de agendamentos
* Dashboard administrativo
* Histórico de alterações
* Autenticação JWT
* Controle de acesso por perfil
* Persistência com PostgreSQL
* Banco H2 para desenvolvimento

---

## Melhorias 

- Implementar validações melhores em cada etapa das regras de negócio, garantindo maior consistência dos dados.
- Adicionar cache com Redis para otimizar consultas frequentes e melhorar o desempenho da aplicação.
- Ampliar a cobertura de testes unitários, integração e segurança.
- Evoluir o gerenciamento de agendamentos com controle de conflitos de horário, cancelamentos, reagendamentos e status do atendimento.
- Adicionar documentação da API com Swagger/OpenAPI.
- Implementar monitoramento e métricas utilizando Spring Boot Actuator.
