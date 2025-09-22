 ## Documentação do Projeto
 Projeto Spring Boot (Java 17, Maven) para coletar dados da The Cat API, armazenar em PostgreSQL e expor via APIs REST.

 ## Documentação das APIs
 - **GET /api/breeds**: Lista todas as raças de gatos.
 - **GET /api/breeds/{id}**: Detalhes de uma raça por ID (ex.: `/api/breeds/abys`).
 - **GET /api/breeds/temperament/{temperament}**: Raças com o temperamento especificado (ex.: `/api/breeds/temperament/playful`).
 - **GET /api/breeds/origin/{origin}**: Raças de uma origem específica (ex.: `/api/breeds/origin/Egypt`).

 ## Documentação de Arquitetura
 Veja `architecture.png`:
 - **Client (Postman/Browser)** faz chamadas REST ao **Spring Boot API Server**.
 - **Spring Boot API Server** (Java 17, Maven) usa JPA/Hibernate para consultar/salvar no **PostgreSQL**.
 - **Ingestão**: `CatIngestServer` usa threads paralelas para buscar dados da **The Cat API** (raças, 3 imagens normais por raça, 3 imagens gerais de chapéu e óculos) e salva no banco.

 ## Instruções para Subir Localmente
 1. **Pré-requisitos**:
    - Java 17, Maven, PostgreSQL, Docker Desktop.
    - Configure a variável de ambiente `CAT_API_KEY`:
      - Windows (PowerShell): `$env:CAT_API_KEY="live_MgmISyqjKq3jC482vJhYwRJX0MeHWmTh8SDWQ3OhnviHWfa9nQZ4RJtp4bsgLu2m"`
 2. **Configurar PostgreSQL**:
    - Crie o banco `catdb` com usuário `agnescostam` e senha `#Metas1403`.
    - Exemplo: `psql -U postgres`, `CREATE USER agnescostam WITH PASSWORD '#Metas1403';`, `CREATE DATABASE catdb OWNER agnescostam;`
 3. **Rodar Ingestão**:
    - `mvn clean install`
    - `mvn spring-boot:run -Dspring-boot.run.main-class=projetos_Agnes_Costa_Macedo.catapi.CatIngestServer`
 4. **Rodar Servidor**:
    - `mvn spring-boot:run`
 5. **Com Docker**:
    - `mvn clean package`
    - `docker-compose up --build`
 6. Teste as APIs com a coleção Postman (`cat-api-collection.json`).

 ## Observações
 - Banco: PostgreSQL (relacional, ideal para dados estruturados com relações raça-imagem).
 - Logging: SLF4J com níveis INFO/DEBUG/ERROR (veja logs no console).
 - Threads: Usadas na ingestão para buscar imagens em paralelo.