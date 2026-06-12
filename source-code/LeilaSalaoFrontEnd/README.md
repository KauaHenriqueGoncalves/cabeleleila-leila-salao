# Leila Salão FrontEnd

Aplicação web responsável pela interface do sistema de gerenciamento de salão de beleza.

Desenvolvida com Angular 22, a aplicação permite o gerenciamento de clientes, serviços e agendamentos, além de disponibilizar uma área exclusiva para clientes acompanharem seus próprios agendamentos.



# Tecnologias Utilizadas

- Angular 22
- TypeScript
- RxJS
- Angular Router
- Angular Forms (Reactive Forms)
- JWT Decode
- Sass
- Vitest

# Funcionalidades

Perfis suportados:

## Admin

Possui acesso completo ao sistema.

Permissões:

- Dashboard
- Clientes
- Serviços
- Agendamentos
- Histórico

## Client

Possui acesso apenas aos próprios dados.

Permissões:

- Meus Agendamentos
- Novo Agendamento
- Atualização dos próprios agendamentos

### layouts

Layouts utilizados pela aplicação.

Exemplos:

- Layout público
- Layout autenticado

---

# Execução em Desenvolvimento

Instalar dependências:

```bash
npm install
```

Executar aplicação:

```bash
ng serve
```

A aplicação estará disponível em:

```text
http://localhost:4200
```

---

# Build

Gerar versão de produção:

```bash
ng build
```

Os arquivos gerados serão disponibilizados na pasta:

```text
dist/
```

# Usuário Administrador

Credenciais disponíveis para acesso administrativo:

```text
Email: admin@admin.com
Senha: admin
```