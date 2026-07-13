# Blog App APIs

A RESTful backend API for a blogging platform, built with **Spring Boot 3.5.3** and **Java 17**. It provides JWT-secured endpoints for managing users, posts, categories, comments, and post images, with Redis-backed rate limiting and Swagger/OpenAPI documentation.

## Features

- **JWT Authentication & Authorization** — stateless login/register flow with role-based access (`ROLE_ADMIN`, `ROLE_NORMAL`)
- **User Management** — CRUD operations for users
- **Post Management** — create, update, delete, search, and paginate blog posts, scoped by user and category
- **Category Management** — CRUD operations for post categories
- **Comment Management** — add and delete comments on posts
- **Image Upload/Download** — attach and retrieve images for posts
- **Rate Limiting** — Redis + Bucket4j/Redisson based request throttling
- **API Documentation** — Swagger UI via springdoc-openapi
- **Global Exception Handling** — consistent error responses across the API
- **Object Mapping** — ModelMapper for entity-DTO conversion

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.3 |
| Build Tool | Maven |
| Security | Spring Security, JWT (jjwt 0.12.6) |
| Database | MySQL (via Spring Data JPA / Hibernate) |
| Caching / Rate Limiting | Redis, Redisson, Bucket4j |
| API Docs | springdoc-openapi (Swagger UI) |
| Object Mapping | ModelMapper |
| Utilities | Lombok |

## Project Structure

```
src/main/java/com/backend/blog/
├── config/            # App configuration (Security, Swagger, App constants)
├── controllers/        # REST controllers (Auth, User, Post, Category, Comment)
├── entities/           # JPA entities (User, Role, Post, Category, Comment)
├── exceptions/         # Custom exceptions & global exception handler
├── payloads/           # DTOs and request/response wrappers
├── repositories/       # Spring Data JPA repositories
├── security/           # JWT filter, token helper, user details service
└── services/           # Service interfaces and implementations (impl/)
```

## Prerequisites

- Java 17+
- Maven 3.6+ (or use the bundled `./mvnw`)
- MySQL Server
- Redis Server

## Configuration

The app uses Spring profiles: `dev` (default, active in `application.properties`) and `prod`.

### `src/main/resources/application.properties`
Sets the server port (`9090`), Redis host/port, file upload limits, and reads the JWT secret from the `JWT_SECRET` environment variable.

### `src/main/resources/application-dev.properties`
Local MySQL connection settings (update as needed):
```properties
spring.datasource.url = jdbc:mysql://localhost:3307/blog_app_apis
spring.datasource.username = root
spring.datasource.password = root
```

### `src/main/resources/application-prod.properties`
Production database connection settings — update these credentials/host before deploying, and avoid committing real secrets to version control.

### Environment Variables

Before running the app, set the JWT secret:

```bash
export JWT_SECRET=your-secret-key
```

## Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd blog-app-apis
   ```

2. **Create the MySQL database**
   ```sql
   CREATE DATABASE blog_app_apis;
   ```

3. **Start Redis** (default `localhost:6379`)
   ```bash
   redis-server
   ```

4. **Set the JWT secret environment variable**
   ```bash
   export JWT_SECRET=your-secret-key
   ```

5. **Build the project**
   ```bash
   ./mvnw clean install
   ```

6. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

   The API will be available at `http://localhost:9090`.

## API Documentation

Once the app is running, explore the API via Swagger UI:

```
http://localhost:9090/swagger-ui/index.html
```

## API Overview

### Auth (`/api/v1/auth`) — public
| Method | Endpoint | Description |
|---|---|---|
| POST | `/login` | Authenticate and obtain a JWT |
| POST | `/register` | Register a new user |

### Users (`/api/users`)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/create` | Create a user |
| GET | `/` | Get all users |
| GET | `/{userId}` | Get a user by ID |
| PUT | `/{userId}` | Update a user |
| DELETE | `/{userId}` | Delete a user |

### Categories (`/api/categories`)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/` | Create a category |
| GET | `/` | Get all categories |
| GET | `/{categoryId}` | Get a category by ID |
| PUT | `/{categoryId}` | Update a category |
| DELETE | `/{categoryId}` | Delete a category |

### Posts (`/api`)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/user/{userId}/category/{categoryId}/posts` | Create a post |
| GET | `/posts` | Get all posts (paginated & sortable) |
| GET | `/posts/{postId}` | Get a post by ID |
| GET | `/user/{userId}/posts` | Get posts by user |
| GET | `/category/{categoryId}/posts` | Get posts by category |
| GET | `/posts/search/{keyword}` | Search posts by title |
| PUT | `/posts/{postId}` | Update a post |
| DELETE | `/posts/{postId}` | Delete a post |
| POST | `/post/image/upload/{postId}` | Upload an image for a post |
| GET | `/post/image/{imageName}` | Download a post image |

### Comments (`/api`)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/post/{postId}/comment` | Add a comment to a post |
| DELETE | `/comment/{commentId}` | Delete a comment |

> All endpoints other than those under `/api/v1/auth/**` and the Swagger docs require a valid JWT (`Authorization: Bearer <token>` header).

## Pagination Defaults

Post listing endpoints support pagination and sorting with the following defaults:
- Page number: `1`
- Page size: `5`
- Sort by: `postId`
- Sort direction: `asc`

## Running Tests

```bash
./mvnw test
```

## Build for Production

```bash
./mvnw clean package
java -jar target/blog-app-apis-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## License

Specify your project license here.
