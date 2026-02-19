# 🚀 Membership Management System

A production-ready **Spring Boot** backend application for managing members with secure authentication, role-based authorization, soft delete support, dynamic filtering, localization, and comprehensive API documentation.

---

## 🧱 Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 4.x |
| Spring Security | 6.x |
| Hibernate / JPA | 6.x |
| Database | H2 (dev/test) |
| JWT | jjwt |
| Validation | Jakarta Validation |
| OpenAPI | springdoc-openapi |
| Testing | JUnit 5 |

---

## 🏗 Architecture Overview

The project follows a clean layered architecture:

```
Controller
   ↓
Facade
   ↓
Service
   ↓
Repository
```

| Layer | Responsibility |
|---|---|
| **Controller** | REST endpoints, request validation, Swagger docs, security annotations |
| **Facade** | DTO ↔ Entity mapping, business orchestration |
| **Service** | Business logic, filtering, soft delete handling |
| **Repository** | Data access, JPA Specifications |

---

## 🔐 Security

The application uses **JWT Authentication** with a stateless session policy and role-based access control.

**Roles:**
- `ROLE_ADMIN`
- `ROLE_USER`

**Protected Endpoints Example:**

| Method | Endpoint | Role |
|---|---|---|
| `POST` | `/api/v1/members` | ADMIN only |
| `GET` | `/api/v1/members/{id}` | ADMIN, USER |
| `GET` | `/api/v1/members` | ADMIN |

Custom `AuthenticationEntryPoint` and `AccessDeniedHandler` are implemented for consistent error responses.

---

## 🌍 Localization (i18n)

All error and success messages are localized based on the request language, resolved via `MessageSource` and the `Accept-Language` header.

```
Accept-Language: ar
Accept-Language: en
```

Validation messages use keys from `messages.properties`.

---

## 🧠 Soft Delete Implementation

Entities inherit from `AbstractEntity`:

```java
@SQLDelete(sql = "UPDATE #{#entityName} SET deleted = true WHERE id=?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
```

To exclude deleted records:

```java
filterService.enableDeletedFilter(false); // deleted = false → active records only
```

---

## 🔎 Dynamic Search & Filtering

Members support advanced filtering using **JPA Specifications**.

**Supported Filters:**

| Filter | Match Type |
|---|---|
| `firstName` | Partial |
| `lastName` | Partial |
| `email` | Partial |
| `mobileNumber` | Partial |
| `gender` | Exact |
| `membershipType` | Exact |
| `persona` | Exact |
| `page`, `size`, `sortBy`, `sortDirection` | Pagination & Sorting |

**Example Request:**

```
GET /api/v1/members?firstName=mo&gender=MALE&page=0&size=10&sortBy=createdAt&sortDirection=DESC
```

---

## 📄 Swagger Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

All endpoints are fully documented using `@Tag`, `@Operation`, `@ApiResponse`, schema definitions, and examples.

---

## 🧪 Testing

Integration tests are written using **H2 in-memory database**, `@SpringBootTest`, and `@Transactional`.

**Tested Scenarios:**
- Create member
- Search without filters
- Search with filters
- Empty result handling

**Test DB Configuration:**

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: create-drop
```

---

## 📦 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/auth/register` | Register user |
| `POST` | `/api/v1/auth/login` | Login & get JWT |

### 👤 Member Management

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/members` | Create member |
| `GET` | `/api/v1/members/{id}` | Get by ID |
| `PUT` | `/api/v1/members/{id}` | Full update |
| `PATCH` | `/api/v1/members/{id}` | Partial update |
| `DELETE` | `/api/v1/members/{id}` | Soft delete |
| `GET` | `/api/v1/members` | Search & filter |

---

## 🧩 Exception Handling

Centralized exception handling via `@RestControllerAdvice`.

**Custom Exceptions:**
- `MemberException`
- `UserException`
- `ValidationException`
- `LanguageNotSupportedException`

---

## 📑 Standard API Response Structure

```json
{
  "message": "Member created successfully",
  "data": { ... }
}
```

**Pagination Response:**

```json
{
  "message": "Member created successfully",
"data": {
  "content": [],
  "totalElements": 0,
  "totalPages": 0,
  "size": 20,
  "number": 0
  }
}
```

---

## ▶️ Running the Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🧠 Design Decisions

- Clean layered architecture with no business logic in controllers
- DTO separation from entities
- Enum validation via custom `@ValidEnum`
- Hibernate soft delete filter
- Stateless JWT security
- Localized message resolution
- Fully documented APIs

---

## 📌 Future Improvements

- [ ] Docker support
- [ ] PostgreSQL production profile
- [ ] Refresh token implementation
- [ ] Audit logging
- [ ] Caching layer (Redis)

---

## 👨‍💻 Author

**Mohammed T. Nasro**  
