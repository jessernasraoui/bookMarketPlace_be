📚 Books Marketplace – Backend
🧩 Project Overview

Books Marketplace is a Spring Boot–based web platform that enables users to buy and sell books online.
The backend provides secure APIs for user management, book operations, orders, and carts, with optimized performance through Redis caching and advanced search capabilities using Elasticsearch.

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

⚙️ Layer Responsibilities

| Layer          | Responsibility                                                                                                                     |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **Controller** | Exposes REST endpoints and handles HTTP requests/responses.                                                                        |
| **Service**    | Encapsulates business logic; each service is defined by an interface and implemented in an `Impl` class (e.g., `BookServiceImpl`). |
| **Repository** | Manages data access using Spring Data JPA and Elasticsearch repositories.                                                          |
| **Entity**     | Represents database models mapped with JPA annotations.                                                                            |
| **DTOs**       | Define clear request and response payloads to separate domain and API layers.                                                      |

🔄 Dependency Flow

Controller → Service (interface) → ServiceImpl → Repository → Database

Controllers depend on Service interfaces (not implementations).

ServiceImpl classes manage domain logic and interact with Repositories.

Repositories communicate directly with PostgreSQL and Elasticsearch.

Redis acts as a cache layer for cart data to improve response times and reduce database load.

🧭 Workflow Guidelines

1.User Authentication

  Handled with Spring Security + JWT for secure API access.

2.Book Management

  CRUD operations for books.

  Books indexed in Elasticsearch for fast full-text search.

3.Cart Management

  Each user has a Redis-backed cart storing items temporarily before checkout.

4.Order Creation

  When a user checks out, cart data is persisted to the Order and OrderItem tables.

5.Order Retrieval

  Users can view their paginated order history.

6.Search

  Search by title, author, or description with Elasticsearch integration.

💻 Development Standards

Java Version: 17

Framework: Spring Boot 3.4.4

Build Tool: Maven

Database: PostgreSQL

Cache: Redis

Search Engine: Elasticsearch

Security: JWT-based authentication

Testing: JUnit

Coding Standards:

  Follow Clean Code principles.

  One service interface per domain.

  Clear exception handling and logging.

  DTOs for all external data exchanges.

🚀 Getting Started
Prerequisites

Ensure the following are installed locally:

  Java 17

  Maven 3.9+

  PostgreSQL

  Redis

  Elasticsearch

Installation:

# Clone the repository
git clone https://github.com/jessernasraoui/bookMarketPlace_be.git
cd books-marketplace-be

# Configure environment
# Edit application.yml or application.properties with your DB and Redis credentials

# Build the project
mvn clean install

# Run the backend
mvn spring-boot:run



│
└── BooksMarketplaceApplication.java  # Application entry point
