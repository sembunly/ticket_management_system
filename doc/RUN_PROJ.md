# Run the Ticket Management System

This repository has seven Spring Boot services and one shared `common` module. The Docker Compose file starts the supporting infrastructure (PostgreSQL, pgAdmin, Redis, ZooKeeper, Kafka, and Zipkin). The Spring Boot services run from Maven on your host; this repository does not currently define Docker images for the application services.

## 1. Prerequisites

- Docker Desktop (or Docker Engine with the Compose v2 plugin)
- Java 21
- Maven 3.9 or newer
- `curl` for the API examples below

Check Java and Maven:

```bash
java -version
mvn -version
```

## 2. Start infrastructure with Docker

From the project root:

```bash
cd deployment/infrastructure
docker compose up -d
docker compose ps
```

Wait for PostgreSQL and Redis to become healthy. View startup logs if a container is not ready:

```bash
docker compose logs -f postgres redis zookeeper kafka
```

The Compose file also starts pgAdmin and Zipkin. Their addresses and development credentials are:

| Component | Address | Login |
|---|---|---|
| PostgreSQL | `localhost:5432` | database `ticketdb`, user `ticketuser`, password `ticketpass` |
| pgAdmin | <http://localhost:5050> | email `sembunly2005@gmail.com`, password `ticketpass` |
| Redis | `localhost:6379` | no password configured |
| Kafka | `localhost:9092` | no authentication configured |
| Zipkin | <http://localhost:9411> | no login |

These are local development credentials. Do not use them for a public or production deployment.

## 3. Create or inspect the PostgreSQL database

On its first start, the Compose PostgreSQL container creates `ticketdb` and the `ticketuser` login from the `POSTGRES_DB`, `POSTGRES_USER`, and `POSTGRES_PASSWORD` values in `docker-compose.yml`. You normally do not need to create them manually.

Connect to the database from the project root:

```bash
docker compose -f deployment/infrastructure/docker-compose.yml exec postgres \
  psql -U ticketuser -d ticketdb
```

Useful `psql` commands:

```sql
\conninfo
\dt
SELECT current_database(), current_user;
\q
```

If PostgreSQL is already running but the database or login does not exist, open a PostgreSQL shell as the Compose administrator (`ticketuser` is the initial admin in a newly created Compose volume):

```bash
docker compose -f deployment/infrastructure/docker-compose.yml exec postgres \
  psql -U ticketuser -d postgres
```

Then create them (omit a statement if that role or database already exists):

```sql
CREATE ROLE ticketuser LOGIN PASSWORD 'ticketpass';
CREATE DATABASE ticketdb OWNER ticketuser;
\q
```

For an existing PostgreSQL volume, changing the Compose environment variables does not change existing database users or passwords. Use `ALTER ROLE ticketuser WITH PASSWORD 'new-password';` from an administrator session if you need to change the password.

### Connect with pgAdmin

1. Open <http://localhost:5050> and sign in with the pgAdmin login in the table above.
2. Choose **Add New Server**.
3. On **General**, enter a name such as `Ticket Management Local`.
4. On **Connection**, enter host `postgres`, port `5432`, maintenance database `ticketdb`, username `ticketuser`, and password `ticketpass`.
5. Save. Use host name `postgres` because pgAdmin itself runs inside the Compose network. Applications launched on your host use `localhost` instead.

You can also connect with a local `psql` client:

```bash
psql "postgresql://ticketuser:ticketpass@localhost:5432/ticketdb"
```

## 4. Configure the services for PostgreSQL

The default `application.yml` files point to the local Compose database and use `ticketuser` / `ticketpass`. Override these values when needed:

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/ticketdb'
export SPRING_DATASOURCE_USERNAME='ticketuser'
export SPRING_DATASOURCE_PASSWORD='ticketpass'
```

The JPA services use `ddl-auto: update`, so Hibernate creates or updates their tables while retaining existing rows across normal restarts. Keep the named Docker volume: `docker compose down` stops containers but preserves data. **Do not use `docker compose down -v` unless you intend to erase the PostgreSQL and Redis volumes.**

The API gateway initializes its `api_routes` and `request_logs` tables from `api-gateway/src/main/resources/schema.sql` when it starts. To enable the example user-service routes through the gateway, run the seed script once from the project root:

```bash
docker compose -f deployment/infrastructure/docker-compose.yml exec -T postgres \
  psql -U ticketuser -d ticketdb \
  < api-gateway/src/main/resources/scripts/insert-default-routes.sql
```

The seed script inserts routes for user-service only. Call user registration/login directly on port 8081 first if you have not started the gateway.

## 5. Build the project

From the project root, build and install all modules (including `common`):

```bash
mvn clean install -DskipTests
```

## 6. Run the Spring Boot services

Run each command in a separate terminal from the project root. Start user-service before the API gateway; start PostgreSQL, Redis, and Kafka first.

```bash
mvn -pl user-service -am spring-boot:run
```

```bash
mvn -pl api-gateway -am spring-boot:run
```

```bash
mvn -pl event-service -am spring-boot:run
```

```bash
mvn -pl ticket-service -am spring-boot:run
```

```bash
mvn -pl order-service -am spring-boot:run
```

```bash
mvn -pl payment-service -am spring-boot:run
```

```bash
mvn -pl notification-service -am spring-boot:run
```

| Service | Port | Swagger UI |
|---|---:|---|
| API gateway | 8080 | <http://localhost:8080/swagger-ui/index.html> |
| User | 8081 | <http://localhost:8081/swagger-ui/index.html> |
| Event | 8082 | <http://localhost:8082/swagger-ui/index.html> |
| Ticket | 8083 | <http://localhost:8083/swagger-ui/index.html> |
| Order | 8084 | <http://localhost:8084/swagger-ui/index.html> |
| Payment | 8085 | <http://localhost:8085/swagger-ui/index.html> |
| Notification | 8086 | <http://localhost:8086/swagger-ui/index.html> |

For the OpenAPI JSON document, use `http://localhost:<port>/v3/api-docs` (for example, <http://localhost:8081/v3/api-docs>). Stop a service with **Ctrl+C** in its terminal.

## 7. Register a user and log in

The user-service seeds the `USER` and `ADMIN` roles when it starts. Registration is public and creates a normal `USER` when `roles` contains `USER`.

Register a test account directly with user-service:

```bash
curl -i -X POST http://localhost:8081/api/public/users/registration \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "alice",
    "password": "ChangeMe123!",
    "email": "alice@example.com",
    "first_name": "Alice",
    "last_name": "Example",
    "roles": ["USER"]
  }'
```

Log in with the same username and password:

```bash
curl -i -X POST http://localhost:8081/api/public/users/login \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "alice",
    "password": "ChangeMe123!"
  }'
```

A successful response contains `data.access_token` and `data.refresh_token`. Send the access token to protected endpoints as:

```http
Authorization: Bearer <access_token>
```

The API gateway login route is `/api/public/users/login`; registration is also available through the gateway at `/api/public/users/registration` after the default routes have been seeded and user-service is running. For local development, direct user-service URLs make it easier to isolate authentication problems.

## 8. Stop and inspect Docker services

Run these commands from `deployment/infrastructure`:

```bash
docker compose ps
docker compose logs -f postgres
docker compose down
```

`docker compose down` preserves named volumes. The following command removes them and permanently deletes local database/cache contents:

```bash
docker compose down -v
```

Only run the volume-removal command when you intentionally want a fresh local database.

## Troubleshooting

- **Port already in use:** stop the existing process using that port, or change the service port in its `application.yml`.
- **PostgreSQL connection refused:** check `docker compose ps` and `docker compose logs postgres`; the services must use host `localhost` when started from Maven on your host.
- **Login returns invalid credentials:** register the user first; the `USER` role is seeded when user-service starts. Registration requires a non-null `roles` array, such as `["USER"]`.
- **Gateway returns 404 for a user route:** confirm the default gateway route seed SQL has been run and the target service is running.
- **Kafka connection errors:** confirm the Kafka container is ready with `docker compose logs kafka`. The app configuration uses `localhost:9092` for host-run services.
- **Fresh database needed:** `docker compose down -v` removes all local volume data; use only when deletion is intended.
