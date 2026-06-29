# Sahyog Samarpan Seva Foundation — Backend

Spring Boot REST API for the Sahyog Samarpan Seva Foundation admin/dashboard.
The public website (Next.js) lives in a separate repository and is deployed on
Vercel; this service is deployed on **Railway** and uses **Neon PostgreSQL**.

## Tech stack

- Java 17, Spring Boot 3.5.16 (Maven)
- Spring Web, Spring Data JPA, Spring Security, Validation
- PostgreSQL (Neon) in dev/prod, H2 in tests
- Actuator (health) for Railway health checks

## Project status

Setup phase: project scaffold, configuration, and a health endpoint only.
Features (JWT auth, dashboard, CRUD, etc.) are added phase by phase.

## Configuration

All secrets/config come from environment variables. See [`.env.example`](./.env.example).

| Variable | Purpose |
| --- | --- |
| `DATABASE_URL` | JDBC URL, e.g. `jdbc:postgresql://HOST/db?sslmode=require` |
| `DB_USERNAME` / `DB_PASSWORD` | Neon credentials |
| `JWT_SECRET` | Long random string (Phase 2+) |
| `JWT_EXPIRATION_MS` | Token lifetime, default `86400000` (24h) |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins (set in Phase 5) |
| `SPRING_PROFILES_ACTIVE` | `dev` locally, `prod` on Railway |
| `PORT` | Injected by Railway at runtime |

Profiles:
- `application.properties` — shared defaults (reads env vars)
- `application-dev.properties` — local development (Neon `dev` branch)
- `application-prod.properties` — Railway (Neon `main` branch)
- test profile uses in-memory H2 (no DB needed to build)

## Running locally

1. Copy the env template and fill in your Neon `dev` branch credentials:
   ```bash
   cp .env.example .env
   ```
2. Export the variables (PowerShell example):
   ```powershell
   $env:DATABASE_URL="jdbc:postgresql://YOUR-HOST/neondb?sslmode=require"
   $env:DB_USERNAME="..."; $env:DB_PASSWORD="..."
   $env:SPRING_PROFILES_ACTIVE="dev"
   ```
3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Verify:
   ```
   GET http://localhost:8080/api/health  ->  {"status":"UP", ...}
   ```

## Build & test

```bash
./mvnw clean package
```

Tests run against in-memory H2, so no database connection is required to build.

## Deployment (Railway)

Railway builds from this repo (Nixpacks detects Maven + Java 17 via `system.properties`).
Set the environment variables above in the Railway service, with
`SPRING_PROFILES_ACTIVE=prod`. Railway provides `PORT` automatically and uses
`/api/health` (or `/actuator/health`) for health checks.
