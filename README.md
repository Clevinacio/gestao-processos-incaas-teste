# Desafio Técnico - Backend Java Spring Boot: Gestão de Processos Judiciais

Este projeto implementa uma API RESTful para gerenciar processos judiciais e suas respectivas audiências, com regras de negócio específicas para o agendamento. Ele foi desenvolvido com foco em modularidade, validação e documentação.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot (Spring Web, Spring Data JPA, Validation)
- Banco de Dados H2 (em memória)
- Lombok (para reduzir boilerplate code)
- Springdoc OpenAPI / Swagger UI (para documentação da API)
- Maven (para gerenciamento de dependências)

## Requisitos Funcionais Implementados

- **Cadastro de Processo Judicial**: Permite cadastrar processos com número único, vara, comarca, assunto e status (ATIVO, ARQUIVADO, SUSPENSO). Possui listagem e filtragem por status e comarca.
- Validação do formato do número do processo (`0000000-00.0000.0.00.0000`) via regex.
- **Agendamento de Audiências**: Cada processo pode ter uma ou mais audiências, com data e hora, tipo e local.
  - Não permite agendamento de novas audiências para processos com status "ARQUIVADO" ou "SUSPENSO".
  - Audiências só podem ser marcadas em dias úteis (segunda a sexta-feira).
  - Não permite sobreposição de audiências na mesma vara e local.
- **Consulta de Agenda**: Endpoint para retornar a agenda de audiências de uma comarca em um determinado dia.

## Diferenciais Implementados

- **Versionamento da API**: A API é versionada via URL (`/api/v1/`). O prefixo `api.base-path` e `api.version` são configuráveis no `application.properties`.
- **Autenticação Simples com Token Fixo**: Um filtro de segurança simples foi implementado para proteger os endpoints da API, exigindo um token fixo no cabeçalho Authorization.
- **Documentação da API com Swagger/OpenAPI**: A API é totalmente documentada e pode ser explorada através da interface Swagger UI.

## Como Rodar o Projeto

Siga as instruções abaixo para configurar e executar o projeto em sua máquina local.

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- JDK 17 ou superior
- Maven

### 1. Clonar o Repositório

```bash
git clone [https://github.com/seu-usuario/nome-do-seu-repositorio.git](https://github.com/Clevinacio/gestao-processos-incaas-teste)
cd gestao-processos-incaas-teste
```

### 2. Compilar o Projeto

```bash
mvn clean install
```

### 3. Configurar o Token de Autenticação

No arquivo `src/main/resources/application.properties`, adicione (ou verifique) a seguinte linha:

```properties
app.auth.token=seu_token_secreto_aqui_para_teste
```

Substitua `seu_token_secreto_aqui_para_teste` por qualquer string que você deseje usar como token. Este token será necessário para todas as requisições autenticadas.

### 4. Configurar o Versionamento da API (Opcional)

No mesmo arquivo `src/main/resources/application.properties`, você pode configurar a versão da API:

```properties
api.base-path=/api
api.version=v1
```

Para mudar a versão da API, basta alterar o valor de `api.version`.

### 5. Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação será iniciada e estará acessível em `http://localhost:8080`.

## Documentação da API (Swagger UI)

Após iniciar a aplicação, você pode acessar a documentação interativa da API através do Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## Autenticação

Esta API utiliza um mecanismo de autenticação simples baseado em um token fixo. Para acessar os endpoints, você deve incluir o cabeçalho `Authorization` em suas requisições HTTP, no formato:

```
Bearer <seu_token_secreto>
```

Exemplo de Token: Se você configurou `app.auth.token=meu_token_secreto`, então o token a ser usado é `meu_token_secreto`.

# Testes e Cobertura de Código

## Rodando os Testes

Para executar todos os testes unitários e de integração do projeto, utilize o comando Maven:

```bash
mvn test
```

## Gerando Relatório de Cobertura de Código (JaCoCo)

Para gerar o relatório de cobertura de código (Code Coverage) com JaCoCo, execute o comando Maven:

```bash
mvn clean verify
```

Este comando irá compilar o projeto, executar os testes e, em seguida, gerar o relatório de cobertura.

## Visualizando os Relatórios
- **Relatório de Cobertura de Código (JaCoCo)**: Após a execução do comando `mvn clean verify`, o relatório de cobertura HTML estará disponível em:
  ```
  target/site/jacoco/index.html
  ```

Basta abrir esses arquivos em seu navegador para visualizar os resultados detalhados dos testes e da cobertura.
