# Java Microservices Onboard Project

A comprehensive microservices architecture built with Spring Boot, Spring Cloud, and event-driven messaging using Apache Kafka. This project demonstrates modern enterprise-level Java development practices with service discovery, API gateway, caching, and asynchronous communication.

## 🏗️ Architecture Overview

This project implements a microservices architecture with the following components:

```
                       ┌─────────────────┐
                       │    Gateway      │
                       │   (Port 9090)   │ 
                       └─────────────────┘
                                │
                                │
                       ┌─────────────────┐    
                       │     Eureka      │    
                       │   (Port 8761)   │    
                       └─────────────────┘    
                                │                       
        ┌───────────────────────┴────────────────────────┐
        │                       │                        │
┌─────────────────┐    ┌─────────────────┐     ┌─────────────────┐
│ Authentication  │    │     Product     │     │    Ordering     │
│   (Port 5001)   │    │   (Port 5002)   │     │   (Port 5003)   │
└─────────────────┘    └─────────────────┘     └─────────────────┘
         │                       │                       │
         │                       │                       │
   ┌────────────┐         ┌─────────────┐        ┌─────────────┐
   │    H2      │         │    Redis    │        │     H2 +    │
   │  Database  │         │    Cache    │        │    Kafka    │
   └────────────┘         └─────────────┘        └─────────────┘
```

## 🚀 Services

### 1. Eureka Server (Port 8761)
- **Purpose**: Service discovery and registration
- **Technology**: Spring Cloud Netflix Eureka
- **Description**: Central registry for all microservices, enabling service-to-service communication and load balancing

### 2. API Gateway (Port 9090)
- **Purpose**: Single entry point for all client requests
- **Technology**: Spring Cloud Gateway
- **Features**:
  - Route management and load balancing
  - Service discovery integration
  - Request routing to appropriate microservices
- **Routes**:
  - `/api/authentication/**` → Authentication Service
  - `/api/product/**` → Product Service
  - `/api/ordering/**` → Ordering Service

### 3. Authentication Service (Port 5001)
- **Purpose**: User authentication and authorization
- **Technology**: Spring Boot, Spring Security, JWT
- **Features**:
  - User login/logout
  - JWT token generation and validation
  - Login history tracking
  - H2 in-memory database for user data
- **Database**: H2 (file-based persistence)
- **Endpoints**:
  - `POST /api/v1/login` - User authentication
  - `POST /api/v1/register` - User registration

### 4. Product Service (Port 5002)
- **Purpose**: Product catalog and inventory management
- **Technology**: Spring Boot, Redis, Apache Kafka
- **Features**:
  - Product information management
  - Redis caching for improved performance
  - Kafka messaging for product activation events
  - OpenFeign for inter-service communication
- **Cache**: Redis (TTL: 600 seconds)
- **Messaging**: Apache Kafka producer
- **Endpoints**:
  - `GET /api/v1/products` - Get all products
  - `POST /api/v1/products/activate` - Activate product

### 5. Ordering Service (Port 5003)
- **Purpose**: Order processing and transaction management
- **Technology**: Spring Boot, JPA, Apache Kafka
- **Features**:
  - Order processing
  - Transaction history tracking
  - Kafka consumer for product activation events
  - H2 database for transaction persistence
- **Database**: H2 (file-based persistence)
- **Messaging**: Apache Kafka consumer
- **Endpoints**:
  - `GET /api/v1/transactions` - Order histories

## 🛠️ Technology Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.5.x** - Application framework
- **Spring Cloud 2025.0.0** - Microservices framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **Spring Cloud Gateway** - API Gateway
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud OpenFeign** - Service-to-service communication

### Databases & Caching
- **H2 Database** - In-memory database for development
- **Redis** - Caching and session storage

### Messaging
- **Apache Kafka** - Event streaming and messaging
- **Confluent Platform** - Kafka distribution

### DevOps & Infrastructure
- **Docker Compose** - Container orchestration
- **Gradle** - Build automation
- **Lombok** - Boilerplate code reduction

## 🐳 Infrastructure Services

### Docker Compose Setup
The project includes a complete Docker Compose setup with:

- **Zookeeper** (Port 2181) - Kafka coordination
- **Kafka** (Port 9093) - Message broker with SASL authentication
- **Kafka UI** (Port 8080) - Web interface for Kafka management
- **Redis** (Port 6379) - Caching service with ACL authentication

## 📦 Prerequisites

- **Java 21+**
- **Docker & Docker Compose**
- **Gradle 8.x**

## 🚀 Getting Started

### 1. Start Infrastructure Services
```bash
cd docker
docker-compose up -d
```

This will start:
- Zookeeper
- Kafka with SASL authentication
- Kafka UI (accessible at http://localhost:8080)
- Redis with authentication

### 2. Start Eureka Server
```bash
cd eureka
./gradlew bootRun
```
Access Eureka Dashboard: http://localhost:8761

### 3. Start Gateway Service
```bash
cd gateway
./gradlew bootRun
```

### 4. Start Business Services
Start each service in separate terminals:

```bash
# Authentication Service
cd authentication
./gradlew bootRun

# Product Service
cd product
./gradlew bootRun

# Ordering Service
cd ordering
./gradlew bootRun
```

### 5. Verify Services
- Check Eureka Dashboard for registered services
- All services should appear as "UP" status

## 🔧 Configuration

### Service Ports
- **Eureka Server**: 8761
- **API Gateway**: 9090
- **Authentication**: 5001
- **Product**: 5002
- **Ordering**: 5003

### Database Access
#### Authentication Service H2 Console
- URL: http://localhost:5001/h2-console
- JDBC URL: `jdbc:h2:file:./src/main/resources/data/authentication`
- Username: `authentication`
- Password: `authentication`

#### Ordering Service H2 Console
- URL: http://localhost:5003/h2-console
- JDBC URL: `jdbc:h2:file:./src/main/resources/data/ordering`
- Username: `ordering`
- Password: `ordering`

### Redis Configuration
- Host: localhost
- Port: 6379
- Username: redis
- Password: redis

### Kafka Configuration
- Bootstrap Servers: localhost:9093
- Security Protocol: SASL_PLAINTEXT
- SASL Mechanism: PLAIN
- Username: kafka
- Password: kafka

## 📝 API Documentation

### Authentication Service Endpoints

#### 🔐 Authentication Management
```http
POST http://localhost:9090/api/authentication/v1/login
```
**Function**: User authentication and JWT token generation  
**Request Body**:
```json
{
    "username": "admin",
    "password": "Bt+cr46eTx7pa1Y49zG9Aw=="
}
```
**Response**: JWT token for authenticated requests

```http
POST http://localhost:9090/api/authentication/v1/register
```
**Function**: Register new user account  
**Request Body**:
```json
{
    "username": "newuser",
    "password": "Bt+cr46eTx7pa1Y49zG9Aw=="
}
```
**Response**: Created user entity

```http
POST http://localhost:9090/api/authentication/v1/verify
```
**Function**: Verify JWT token validity  
**Headers**: Authorization: Bearer {jwt_token}  
**Response**: Token verification status and user details

#### 👤 User Management
```http
GET http://localhost:9090/api/authentication/v1/users
```
**Function**: Retrieve all users (Admin only)  
**Response**: List of all registered users

```http
GET http://localhost:9090/api/authentication/v1/users/{id}
```
**Function**: Get user details by ID  
**Parameters**: `id` - User ID  
**Response**: User entity details

```http
PUT http://localhost:9090/api/authentication/v1/users/{id}/update
```
**Function**: Update user information  
**Parameters**: `id` - User ID  
**Request Body**: Updated user data  
**Response**: Updated user entity

```http
DELETE http://localhost:9090/api/authentication/v1/users/{id}/delete
```
**Function**: Delete user account  
**Parameters**: `id` - User ID  
**Response**: Deletion confirmation message

### Product Service Endpoints

#### 📦 Product Catalog Management
```http
GET http://localhost:9090/api/product/v1/products
```
**Function**: Retrieve all available products with caching  
**Features**:
- Redis caching enabled (TTL: 600 seconds)
- External API integration for product data
- Automatic cache refresh on data changes
**Response**: Complete product catalog with details

```http
POST http://localhost:9090/api/product/v1/products/{productId}/activate
```
**Function**: Activate product and trigger order processing  
**Parameters**: `productId` - Product identifier (e.g., P00001)  
**Headers**: Authorization: Bearer {jwt_token}  
**Features**:
- Publishes activation event to Kafka topic
- Triggers asynchronous order processing
- Integrates with external activation API
**Response**: Activation confirmation message

### Ordering Service Endpoints

#### 📋 Transaction Management
```http
GET http://localhost:9090/api/ordering/v1/orders/{username}/histories
```
**Function**: Retrieve order transaction history for specific user  
**Parameters**: `username` - User identifier  
**Headers**: Authorization: Bearer {jwt_token}  
**Features**:
- Persists transaction data in H2 database
- Tracks product activation events
- Maintains audit trail for orders
**Response**: List of transaction history records

### Default Credentials
- Username: `admin`
- Password: `Bt+cr46eTx7pa1Y49zG9Aw==`  (*Encrypted Value**)

## 🧪 Testing

### Using Postman
Import the provided Postman collection: `final-project.postman_collection.json`

### Running Tests
```bash
# Run tests for all services
./gradlew test

# Run tests for specific service
cd authentication
./gradlew test
```

## 🏛️ Architecture Patterns

### Microservices Patterns Implemented
1. **Service Discovery** - Eureka for service registration and discovery
2. **API Gateway** - Single entry point with routing
3. **Circuit Breaker** - Fault tolerance (via Spring Cloud)
4. **Load Balancing** - Client-side load balancing
5. **Event-Driven Architecture** - Kafka for asynchronous messaging
6. **Caching** - Redis for performance optimization
7. **Database per Service** - Each service has its own database

### Design Principles
- **Single Responsibility** - Each service has a specific business capability
- **Decentralized Data Management** - Each service manages its own data
- **Fault Tolerance** - Services are designed to handle failures gracefully
- **Scalability** - Services can be scaled independently

## 🔍 Monitoring & Observability

### Available Dashboards
- **Eureka Dashboard**: http://localhost:8761 - Service registry status
- **Kafka UI**: http://localhost:8080 - Kafka topics and messages
- **H2 Consoles**: Database inspection and queries

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is created for educational purposes as part of Java onboarding program.

---

## 🚨 Troubleshooting

### Common Issues

1. **Services not registering with Eureka**
   - Ensure Eureka server is running
   - Check network connectivity
   - Verify application.properties configuration

2. **Kafka connection issues**
   - Ensure Docker containers are running
   - Check SASL authentication credentials
   - Verify bootstrap servers configuration

3. **Database connection errors**
   - Check H2 database file permissions
   - Verify datasource configuration
   - Ensure application has write access to data directory

4. **Redis connection failures**
   - Verify Redis container is running
   - Check authentication credentials
   - Ensure Redis port is accessible


For more detailed information, refer to the individual service documentation and configuration files.