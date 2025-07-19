# 📚 API-Livros

Uma aplicação Java com Spring Boot que consome a [API Gutendex](https://gutendex.com/) para buscar e armazenar informações sobre livros, autores e seus respectivos dados em um banco de dados PostgreSQL.

---

## 🚀 Funcionalidades

- 🔍 Buscar livro pelo título (via API Gutendex) e salvar no banco de dados
- 📚 Listar todos os livros registrados
- ✍️ Listar todos os autores registrados
- 📆 Listar autores por ano de nascimento
- 🌐 Listar livros por idioma
- ❌ Encerrar aplicação

---

## 🛠 Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **API Gutendex**
- **Lombok**

---

## ⚙️ Requisitos

- Java 17 ou superior (recomendado: Java 21)
- Maven 3.9+
- PostgreSQL instalado e rodando
- IntelliJ IDEA (opcional, mas recomendado)

---

## 💾 Configuração do Banco de Dados

No arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_livros
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

▶️ Executando o projeto
1. Clone o repositório
git clone https://github.com/seu-usuario/api-livros.git
cd api-livros
2. Compile o projeto
mvn clean install
3. Execute
mvn spring-boot:run
Ou direto pela classe Application.java no IntelliJ.

🧪 Exemplos de busca
Títulos: "Pride and Prejudice", "Dom Casmurro", "O Alienista"

Idiomas: "en", "pt", "fr", "es"

🗂 Estrutura de pastas

src/
 └── main/
     └── java/
         └── com.api.livros/
             ├── model/         → Entidades: Livro, Autor
             ├── repository/    → Repositórios JPA
             ├── service/       → Lógica de negócio (LivroService)
             ├── console/       → Menu interativo (MenuConsole)
             └── Application.java
