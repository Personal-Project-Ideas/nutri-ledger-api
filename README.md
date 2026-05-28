# Nutri Ledger API

Simple backend API for nutrition tracking, with Google login and JWT-based session cookie.

## What This Project Is

This project is in an early stage.  
Current focus is building a clean base architecture, authentication flow, and core endpoints.

## Main Technologies

- Java 21
- Spring Boot 3.3
- Spring Web (REST APIs)
- Spring Data JPA
- Spring Security + OAuth2 (Google)
- JWT (`jjwt`)
- PostgreSQL (local/prod), H2 (tests)
- MapStruct
- Lombok
- Maven
- OpenAPI / Swagger UI

## Architecture (Simple View)

The code follows a ports-and-adapters style:

- `domain`: business models and domain errors
- `application`: use cases, ports (interfaces), mappers
- `infra`: persistence adapters, repositories, security/config implementations
- `http`: controllers and DTOs

Why this is useful:

- business rules stay isolated from framework details
- easier testing and refactoring
- clearer responsibility per layer

## Project Structure

```text
src/main/java/io/github/pratesjr/nutriledgerapi
├── application
│   ├── models
│   ├── ports
│   ├── usecases
│   └── mappers
├── domain
│   ├── models
│   └── errors
├── infra
│   ├── config
│   ├── entities
│   ├── persistences
│   ├── repositories
│   ├── security
│   └── services
└── http
    ├── controllers
    └── dtos
```

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL

### 1) Set environment variables

At minimum, define values used by `application.yaml` / profile files:

- `APP_NAME`
- `SPRING_PROFILES_ACTIVE` (`local`, `test`, or `prod`)
- `SERVER_PORT`
- `SERVER_CONTEXT_PATH`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `GCLOUD_CLIENT_ID`
- `GCLOUD_CLIENT_SECRET`
- `GCLOUD_AUTH_URI`
- `GCLOUD_TOKEN_URI`
- `GOOGLE_PEOPLE_API_URL`
- `POSTGRES_HOST`
- `POSTGRES_PORT`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

### 2) Run the API

```bash
mvn spring-boot:run
```

## Tests

Tests use the `test` profile with in-memory H2 (tables created from JPA entities via `ddl-auto: create-drop`). Copy `.env.example` to `.env` and set `TEST_DB_*`, JWT, OAuth, and related variables—tests read configuration from `.env`, not committed property files.

```bash
make test
# or a single class:
make test TEST_CLASS=JwtUtilTest
```

Requires a `.env` with `TEST_DB_*`, JWT, OAuth, and related variables (see `.env.example`). `make test` forces the `test` profile so H2 settings from `.env` are used instead of local Postgres.

## API Docs

When running locally, Swagger is available at:

- `/swagger-ui.html` (or `/swagger-ui/index.html`)

## Current Status

- Core structure is ready
- Authentication flow is being implemented
- Business features are still being expanded
