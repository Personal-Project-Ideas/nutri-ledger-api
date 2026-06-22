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
- `OAUTH2_REDIRECT_URI` (must match Google Console — see below)
- `GOOGLE_PEOPLE_API_URL`
- `POSTGRES_HOST`
- `POSTGRES_PORT`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

### Google OAuth (fix `redirect_uri_mismatch`)

Spring sends this **exact** redirect URI to Google (from `OAUTH2_REDIRECT_URI` in `.env`):

```text
http://localhost:{SERVER_PORT}{SERVER_CONTEXT_PATH}/login/oauth2/code/google
```

Example with defaults (`8080`, `/nutri-ledger/api`):

```text
http://localhost:8080/nutri-ledger/api/login/oauth2/code/google
```

**Important:** you do **not** open that URL yourself. It is not a page. Google calls it after you sign in (with `?code=...&state=...`). If you paste it in the browser alone, Spring redirects away and it looks “broken”.

| URL | Who uses it |
|-----|-------------|
| `GET .../auth/google/signin` | **You** — start sign-in (browser) |
| `GET .../auth/google/signup` | **You** — start sign-up (browser) |
| `GET .../oauth2/authorization/google` | Spring internal redirect to Google (do not bookmark) |
| `GET .../login/oauth2/code/google` | **Google only** — register in Console, never type in browser |

In [Google Cloud Console](https://console.cloud.google.com/) → **APIs & Services** → **Credentials** → your **OAuth 2.0 Client ID** (Web application):

1. **Authorized redirect URIs** → add the callback URI above (exact match, no trailing `/`).
2. Do **not** put `/oauth2/authorization/google` in redirect URIs — that is the authorization start, not the callback.
3. If `SERVER_CONTEXT_PATH` changes, update `.env` (`OAUTH2_REDIRECT_URI`) and Google Console.

Start login in the browser:

```text
http://localhost:8080/nutri-ledger/api/auth/google/signin
```

(or `/auth/google/signup` for registration). After success you land on `APP_OAUTH_LOGIN_SUCCESS_URL` with `AUTH_TOKEN` set on `localhost:8080`.

### 2) Run the API

```bash
make run-local
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
