📚 Books Marketplace – Backend

📋 Table of Contents

🧩 Project Overview

🏗️ Architecture Pattern

📁 Project Structure

⚙️ Layer Responsibilities

🔄 Dependency Flow

🧭 Workflow Guidelines

💻 Development Standards

🚀 Getting Started


🧩 Project Overview

Books Marketplace is a Spring Boot–based web platform that enables users to buy and sell books online.
The backend provides secure REST APIs for:

User management and authentication

Book CRUD operations

Cart management with Redis caching

Order processing

Advanced full-text search with Elasticsearch

🏗️ Architecture Pattern

This project follows the Packaging by Feature architecture pattern.
Each main domain (e.g., book, order, user) contains its controller, service, repository, and related components within a single package — ensuring high cohesion, scalability, and ease of maintenance.

📁 Project Structure
com.jnas.books_marketplace_be
│
├── book/                # Book entity, service, controller, repository
├── user/                # User management and authentication
├── role/                # Role entity and RoleName enum (no service/controller)
├── order/               # Order logic (creation, retrieval)
├── order_items/         # Order item details and mapping
├── cart/                # Cart management using Redis cache
├── BookSearch/          # Elasticsearch integration for book search
│
├── security/            # Spring Security & JWT configuration
├── exception/           # Custom exceptions
├── config/              # App and persistence configurations
│
└── BooksMarketplaceApplication.java  # Application entry point

⚙️ Layer Responsibilities
Layer	Responsibility
Controller	Exposes REST endpoints and handles HTTP requests/responses.
Service	Encapsulates business logic; each service is defined by an interface and implemented in an Impl class (e.g., BookServiceImpl).
Repository	Manages data access using Spring Data JPA and Elasticsearch repositories.
Entity	Represents database models mapped with JPA annotations.
DTOs	Define clear request and response payloads to separate domain and API layers.
🔄 Dependency Flow
Controller → Service (interface) → ServiceImpl → Repository → Database


Controllers depend on Service interfaces (not implementations).

Services handle domain logic and interact with Repositories.

Repositories communicate with:

PostgreSQL → Persistent data

Elasticsearch → Search index

Redis → Cart cache

🧭 Workflow Guidelines
1️⃣ User Authentication

Handled with Spring Security and JWT tokens.

Provides secure login and authorization for protected endpoints.

2️⃣ Book Management

Supports full CRUD operations.

Books are indexed in Elasticsearch for fast full-text search.

3️⃣ Cart Management

Each user has a Redis-backed cart storing items temporarily before checkout.

Cart data is cleared after a successful order.

4️⃣ Order Creation

When a user checks out, cart data is transformed into Order and OrderItem entities and persisted to PostgreSQL.

5️⃣ Order Retrieval

Users can view their paginated order history.

6️⃣ Search

Advanced book search by title, author, or description using Elasticsearch.

💻 Development Standards
Category	Specification
Java Version	17
Framework	Spring Boot 3.4.4
Build Tool	Maven
Database	PostgreSQL
Cache	Redis
Search Engine	Elasticsearch
Security	JWT-based authentication
Testing	JUnit

Coding Standards:

Follow Clean Code principles.

One service interface per domain (e.g., BookService, OrderService).

Consistent exception handling and logging.

Use DTOs for all external API data exchanges.

Prefer constructor injection and transactional boundaries in service implementations.

🚀 Getting Started
🧱 Prerequisites

Make sure the following are installed:

Java 17

Maven 3.9+

PostgreSQL

Redis

Elasticsearch

⚙️ Installation
# Clone the repository
git clone https://github.com/jessernasraoui/bookMarketPlace_be.git
cd books-marketplace-be

# Configure environment
# Edit application.yml or application.properties with your DB and Redis credentials

# Build the project
mvn clean install

# Run the backend
mvn spring-boot:run

🔧 Configuration Example (application.yml)
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/books_db
    username: your_db_user
    password: your_db_pass
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true

  redis:
    host: localhost
    port: 6379

  elasticsearch:
    uris: http://localhost:9200

app:
  jwt:
    secret: change-me-to-a-secure-secret
    expiration-ms: 3600000


✅ Tip:
Start PostgreSQL, Redis, and Elasticsearch before launching the app.
Default base URL after startup:

http://localhost:8081/api/v1/
