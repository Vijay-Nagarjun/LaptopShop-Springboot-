# Laptop Shop Management System

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

A comprehensive backend solution for managing an online laptop store, built with Spring Boot 3.x and following RESTful API best practices.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [System Architecture](#-system-architecture)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#-api-endpoints)
- [Authentication](#-authentication)
- [Testing](#-testing)
- [Security](#-security)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

- **User Management**
  - JWT-based authentication
  - Role-based access control (USER, ADMIN)
  - Secure password hashing with BCrypt

- **Product Catalog**
  - CRUD operations for laptop products
  - Filter laptops by brand, category, and price range
  - Pagination and sorting support

- **Order Processing**
  - Create and manage orders
  - Track order status
  - Order history for customers

- **Customer Management**
  - Customer profiles
  - Order history
  - Address management

- **Admin Dashboard**
  - Product management
  - User management
  - Sales reporting

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.4.3
- **Language**: Java 23
- **Build Tool**: Maven

### Database
- **Primary Database**: MySQL 8.0+
- **ORM**: Spring Data JPA (Hibernate 6.0+)

### Security
- **Authentication**: JWT (JSON Web Tokens)
- **Password Hashing**: BCrypt
- **CORS**: Enabled for cross-origin requests

### Documentation
- **API Docs**: SpringDoc OpenAPI 3.0
- **Interactive UI**: Swagger UI

### Development Tools
- **Lombok**: For reducing boilerplate code
- **H2 Database**: For testing (optional)
- **Actuator**: For monitoring and metrics

## 🏗️ System Architecture

The application follows a clean, layered architecture:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌─────────────────┐             ┌───────────────────┐    │
│  │   Controllers   │─────────────▶│  Exception Handler │    │
│  └─────────────────┘             └───────────────────┘    │
└───────────────────────────────┬─────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────┐
│                     Service Layer                           │
│  ┌─────────────────┐             ┌───────────────────┐    │
│  │  Service Layer  │─────────────▶│     DTOs          │    │
│  └─────────────────┘             └───────────────────┘    │
└───────────────────────────────┬─────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────┐
│                     Data Access Layer                       │
│  ┌─────────────────┐             ┌───────────────────┐    │
│  │   Repositories  │◀────────────│  Entity Classes    │    │
│  └─────────────────┘             └───────────────────┘    │
└───────────────────────────────┬─────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────┐
│                       Database                              │
│  ┌─────────────────────────────────────────────────────┐    │
│  │                  MySQL Database                     │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

## 📚 Database Schema

### Entity Relationship Diagram

```mermaid
erDiagram
    USER ||--o{ CUSTOMER : "has"
    CUSTOMER ||--o{ ORDERS : "places"
    ORDERS ||--o{ ORDER_ITEM : "contains"
    LAPTOP ||--o{ ORDER_ITEM : "appears_in"
    
    USER {
        Long id PK
        String username
        String email
        String password
        Set<String> roles
    }
    
    CUSTOMER {
        Long id PK
        String name
        String email
        String phone
        String address
        Long userId FK
    }
    
    ORDERS {
        Long id PK
        LocalDate orderDate
        Long customerId FK
        Integer version
    }
    
    ORDER_ITEM {
        Long id PK
        Integer quantity
        Long orderId FK
        Long laptopId FK
    }
    
    LAPTOP {
        Long id PK
        String name
        String brand
        Double price
        String category
    }
```

## 📖 API Documentation

Interactive API documentation is available via Swagger UI when the application is running:

```
http://localhost:8080/swagger-ui.html
```

Or view the OpenAPI JSON specification:
```
http://localhost:8080/v3/api-docs
```

## 🚀 Getting Started

### Prerequisites

- Java 23 JDK or later
- MySQL 8.0+ server
- Maven 3.6+ (or use Maven wrapper)
- Git (for version control)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/laptop-shop.git
   cd laptop-shop
   ```

2. **Set up the database**
   ```sql
   CREATE DATABASE laptop_shop;
   CREATE USER 'laptopuser'@'localhost' IDENTIFIED BY 'securepassword';
   GRANT ALL PRIVILEGES ON laptop_shop.* TO 'laptopuser'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Configure the application**
   Copy the `application.properties.example` to `application.properties` and update the database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/laptop_shop?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=laptopuser
   spring.datasource.password=securepassword
   spring.jpa.hibernate.ddl-auto=update
   
   # JWT Configuration
   app.jwtSecret=your-512-bit-secret-key-must-be-at-least-64-characters-long
   app.jwtExpirationMs=86400000
   ```

### Running the Application

1. **Using Maven**
   ```bash
   mvn spring-boot:run
   ```

2. **Build and Run**
   ```bash
   mvn clean package
   java -jar target/laptopshop-0.0.1-SNAPSHOT.jar
   ```

The application will be available at `http://localhost:8080`

## 🔐 Authentication

The API uses JWT for authentication. To access protected endpoints:

1. **Register a new user**
   ```http
   POST /api/auth/signup
   Content-Type: application/json
   
   {
     "username": "user1",
     "email": "user1@example.com",
     "password": "password123"
   }
   ```

2. **Login to get JWT token**
   ```http
   POST /api/auth/signin
   Content-Type: application/json
   
   {
     "username": "user1",
     "password": "password123"
   }
   ```

3. **Use the token in subsequent requests**
   ```http
   GET /api/profile
   Authorization: Bearer <your-jwt-token>
   ```

## 🧪 Testing

### Unit Tests
Run all unit tests:
```bash
mvn test
```

### Integration Tests
To run integration tests (requires test database):
```bash
mvn verify -Pintegration-test
```

### API Testing with Postman
1. Import the `postman_collection.json` into Postman
2. Update environment variables with your base URL and authentication tokens
3. Run the collection to test all API endpoints

## 🔒 Security

### Authentication Flow
1. Client sends credentials to `/api/auth/signin`
2. Server validates credentials and returns JWT token
3. Client includes token in `Authorization: Bearer <token>` header
4. Server validates token on each request

### Security Headers
The application includes the following security headers by default:
- Content Security Policy (CSP)
- X-Content-Type-Options: nosniff
- X-Frame-Options: DENY
- X-XSS-Protection: 1; mode=block
- Strict-Transport-Security: max-age=31536000 ; includeSubDomains

## 🚀 Deployment

### Production Deployment
1. **Build the application**
   ```bash
   mvn clean package -DskipTests
   ```

2. **Docker Deployment**
   ```bash
   # Build the Docker image
   docker build -t laptop-shop .
   
   # Run the container
   docker run -d -p 8080:8080 --name laptop-shop-app \
     -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/laptop_shop \
     -e SPRING_DATASOURCE_USERNAME=user \
     -e SPRING_DATASOURCE_PASSWORD=password \
     -e APP_JWTSECRET=your-secret-key \
     --network=host \
     laptop-shop
   ```

3. **Kubernetes Deployment**
   See the `kubernetes/` directory for sample deployment configurations.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📫 Contact

For any questions or feedback, please reach out to [your-email@example.com](mailto:your-email@example.com)

---

<div align="center">
  Made with ❤️ using Spring Boot
</div>
