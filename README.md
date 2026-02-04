# PsiSoftware

Backend desenvolvido em Java com Spring Boot para gestão de psicólogos, focado em controle de pacientes, sessões e pagamentos.

Este projeto foi criado como **projeto de estudo e portfólio**, com foco em boas práticas de arquitetura, testes unitários e organização de código no contexto de aplicações backend.

---

## 🎯 Objetivo do projeto

O PsiSoftware tem como objetivo simular um sistema real de gestão utilizado por psicólogos, permitindo:

- Cadastro e gerenciamento de pacientes
- Registro de sessões
- Controle de pagamentos
- Simulação de envio de notificações (mensageria interna)

O foco principal não é a interface, mas sim a **lógica de negócio e a estrutura backend**.

---

## 🧱 Stack utilizada

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL (via Docker)
- Maven
- JUnit 5
- Mockito
- GitHub Actions (CI)

---

## 🗂️ Estrutura do projeto

Organização baseada em camadas:

- controller -> entrada da aplicação (HTTP)
- dto -> objetos de transferência de dados
- service -> regras de negócio
- repository -> acesso a dados
- model -> entidades do domínio
- event -> eventos internos da aplicação
- messaging -> simulação de mensageria (email)


---

## 🧠 Decisões arquiteturais

- A camada de **service** concentra toda a regra de negócio
- Controllers utilizam **DTOs**, evitando exposição direta das entidades
- Conversões entre DTOs e entidades são feitas manualmente para manter clareza
- A aplicação utiliza **eventos internos** para desacoplar responsabilidades
- Mensageria é simulada (sem integração externa) para fins de estudo

---

## 📡 Mensageria interna

Ao registrar uma sessão:

1. A sessão é salva
2. Um evento interno (`SessaoCriadaEvent`) é publicado
3. Um listener assíncrono processa o evento
4. Um email simulado é adicionado a uma outbox em memória

Esse fluxo evita acoplamento direto entre regras de negócio e efeitos colaterais.

---

## 🧪 Testes

- Testes unitários focados na camada de service
- Uso de Mockito para isolar dependências
- Testes não utilizam Spring Context
- Pipeline de CI garante que os testes passem antes de qualquer merge

Testes de integração ficaram fora do escopo inicial do projeto.

---

## 🚧 Próximos passos

- Melhorar documentação
- Criar testes de integração
- Evoluir regras de negócio
- 
## ▶️ Como rodar o projeto localmente

A aplicação roda por padrão na porta **8081** e utiliza **MySQL executado via Docker**.

1. Suba o banco de dados:

docker compose up -d

2. Execute os testes:

mvn test


3. Inicie a aplicação:

mvn spring-boot:run
