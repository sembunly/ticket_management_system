# Ticket Management System

A Spring Boot microservices backend for managing users, events, tickets, orders, payments, and notifications.

## Services

| Service | Port | Purpose |
|---|---:|---|
| API Gateway | 8080 | Routing, token verification, rate limiting, and request logging |
| User Service | 8081 | Registration, login, users, roles, groups, and permissions |
| Event Service | 8082 | Event creation and management |
| Ticket Service | 8083 | Ticket creation, lookup, locking, and unlocking |
| Order Service | 8084 | Order creation, lookup, cancellation, and payment coordination |
| Payment Service | 8085 | Simulated payment processing |
| Notification Service | 8086 | Simulated email and SMS notifications from Kafka events |

## Technology

- Java 21 and Spring Boot
- Maven
- PostgreSQL
- Redis
- Apache Kafka and ZooKeeper
- Docker Compose
- Zipkin

## Requirements

- Java 21
- Maven 3.9 or newer
- Docker with Docker Compose

## Quick Start

Start the infrastructure:

```bash
docker compose -f deployment/infrastructure/docker-compose.yml up -d
```

Build all modules:

```bash
mvn clean install -DskipTests
```

Run each service in a separate terminal:

```bash
mvn -pl user-service -am spring-boot:run
mvn -pl api-gateway -am spring-boot:run
mvn -pl event-service -am spring-boot:run
mvn -pl ticket-service -am spring-boot:run
mvn -pl order-service -am spring-boot:run
mvn -pl payment-service -am spring-boot:run
mvn -pl notification-service -am spring-boot:run
```

Swagger UI is available at:

```text
http://localhost:<port>/swagger-ui/index.html
```

For example, the User Service documentation is at:

```text
http://localhost:8081/swagger-ui/index.html
```

## Basic Authentication Flow

Register a user:

```bash
curl -X POST http://localhost:8081/api/public/users/registration \
  -H "Content-Type: application/json" \
  -d '{"username":"sokha","password":"Sokha123!","email":"sokha@example.com","first_name":"Sokha","last_name":"Chan"}'
```

Log in:

```bash
curl -X POST http://localhost:8081/api/public/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"sokha","password":"Sokha123!"}'
```

Use the returned access token on protected endpoints:

```http
Authorization: Bearer <access_token>
```

## Local Infrastructure

| Component | Address |
|---|---|
| PostgreSQL | `localhost:5432` |
| pgAdmin | <http://localhost:5050> |
| Redis | `localhost:6379` |
| Kafka | `localhost:9092` |
| Zipkin | <http://localhost:9411> |

The local PostgreSQL database is `ticketdb`, with username `ticketuser` and password `ticketpass`. These credentials are for local development only.

## Current Limitations

- This repository contains backend services only; no web or mobile interface is included.
- Event and ticket APIs support lookup by ID but do not provide public list or search endpoints.
- Payment methods are simulated and do not connect to a real payment provider.
- Email and SMS delivery is simulated through application logs.
- Order creation does not yet validate or reserve tickets through the Event and Ticket services.
- Default API Gateway seed data contains routes for the User Service only. Other services can be called directly on their ports.

## Documentation

- [Project setup and execution](doc/RUN_PROJ.md)
- [Khmer usage guide](doc/HOW_TO_USE_PROJECT.md)
- [Architecture](doc/ARCHITECTURE.md)
- [API Gateway token verification](doc/API_GATEWAY_TOKEN_VERIFICATION.md)

## Stop the Project

Stop each Spring Boot service with `Ctrl+C`, then stop the infrastructure:

```bash
docker compose -f deployment/infrastructure/docker-compose.yml down
```

This command preserves local volumes. Do not add `-v` unless you intend to delete the local database and cache data.
