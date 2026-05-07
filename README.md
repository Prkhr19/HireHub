# HireHub
HireHub is a backend job portal application built using Spring Boot. It supports recruiter and candidate workflows including authentication, job posting, filtering, company management, and role-based authorization.

## API Documentation

https://hirehub-stsb.onrender.com/api/v1/swagger-ui/index.html#
## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- Hibernate / JPA
- JPA Specifications
- Swagger / OpenAPI
- Docker
- Render

  ## Features

- JWT-based Authentication & Authorization
- Role-based Access Control
- Dynamic Job Search & Filtering using JPA Specifications
- Recruiter & Candidate Workflows
- Company & Job Management
- Global Exception Handling
- Swagger API Documentation
- Dockerized Deployment

  ## Architecture

The project follows layered architecture:

- Controller Layer
- Service Layer
- Repository Layer
- Security Layer
- DTO-based API communication

## Environment Variables

DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
JWT_EXPIRATION=

## Run Locally

Clone the repository:

```bash
git clone https://github.com/Prkhr19/HireHub.git
cd HireHub
```

Run the application:

```bash
./mvnw spring-boot:run
```

---

## Run with Docker

Build Docker image:

```bash
docker build -t hirehub .
```

Run container:

```bash
docker run -p 8080:8080 hirehub
```

---

## Deployment

The application is containerized using Docker and deployed on Render with PostgreSQL as the production database.
