# Expense Reviewer

A **Spring Boot REST API** for managing and reviewing employee expense claims.

The application allows users to create, update, view, approve, reject, and delete expense claims. It also provides policy validation, filtering, pagination, sorting, exception handling, database persistence, unit testing, and Swagger API documentation.

---

## Features

- Create and manage expense claims
- Approve or reject claims
- Validate expense claims against policy rules
- Input validation using Jakarta Validation
- Search claims by claimant
- Filter claims by category and status
- Pagination and sorting
- Global exception handling
- MySQL database persistence
- Unit testing using JUnit and Mockito
- Swagger API documentation

---

## Technologies

| Technology         | Purpose                         |
| ------------------ | ------------------------------- |
| Java 25            | Programming language            |
| Spring Boot 3.5.16 | Backend framework               |
| Spring Web         | REST API development            |
| Spring Data JPA    | Database access                 |
| Hibernate          | ORM                             |
| MySQL 8.4          | Database                        |
| Maven              | Build and dependency management |
| Jakarta Validation | Input validation                |
| JUnit 5            | Unit testing                    |
| Mockito            | Mocking for tests               |
| Swagger / OpenAPI  | API documentation               |

---

## Project Structure

```text
expense_reviewer
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.expensereviewer
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       └── service
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── com.expensereviewer
│               └── service
│
├── pom.xml
└── README.md
```

---

## Database Setup

The project uses **MySQL**.

Create the database:

```sql
CREATE DATABASE expense_reviewer;
```

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_reviewer
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

Replace `YOUR_PASSWORD` with your local MySQL password.

---

## Running the Application

### 1. Start MySQL

Make sure the MySQL server is running.

### 2. Open the project

```text
C:\expensereviewer\expense_reviewer
```

### 3. Start the application

```bash
mvn spring-boot:run
```

The application will run at:

```text
http://localhost:8080
```

---

## REST API

### Claim Management

| Method | Endpoint                   | Description     |
| ------ | -------------------------- | --------------- |
| POST   | `/api/claims`              | Create a claim  |
| GET    | `/api/claims`              | Get all claims  |
| GET    | `/api/claims/{id}`         | Get claim by ID |
| PUT    | `/api/claims/{id}`         | Update a claim  |
| DELETE | `/api/claims/{id}`         | Delete a claim  |
| PUT    | `/api/claims/{id}/approve` | Approve a claim |
| PUT    | `/api/claims/{id}/reject`  | Reject a claim  |

### Search and Filtering

| Method | Endpoint                          | Description        |
| ------ | --------------------------------- | ------------------ |
| GET    | `/api/claims/category/{category}` | Filter by category |
| GET    | `/api/claims/status/{status}`     | Filter by status   |
| GET    | `/api/claims/claimant/{claimant}` | Search by claimant |

### Pagination and Sorting

```text
GET /api/claims/page?page=0&size=5
```

Sort by amount:

```text
GET /api/claims/page?page=0&size=5&sort=amount,desc
```

### Policy Validation

```text
POST /api/policy/validate
```

This endpoint checks an expense claim against the configured policy rules.

---

## Sample Request

```json
{
  "claimant": "John",
  "date": "2026-10-06",
  "category": "Travel",
  "amount": 2000,
  "currency": "INR",
  "description": "Travel expense",
  "receiptAvailable": true
}
```

A valid claim passes policy validation.

If a claim violates a policy, the API returns a validation message such as:

```text
Amount exceeds the Travel limit of 10000.00 INR.
```

---

## Claim Status

A newly created claim has the status:

```text
PENDING
```

The claim can then move to either:

```text
PENDING
   |
   +----> APPROVED
   |
   +----> REJECTED
```

---

## Validation and Exception Handling

The application validates:

- Required fields
- Positive expense amount
- Valid claim information

Invalid requests return:

```text
400 Bad Request
```

If a requested claim does not exist, the application returns:

```text
404 Not Found
```

with a message such as:

```text
Claim not found with id: 129
```

Global exception handling is implemented using `@RestControllerAdvice`.

---

## Testing

Unit tests are implemented using:

- JUnit 5
- Mockito

Run the tests using:

```bash
mvn test
```

The current test suite verifies:

- Successful claim retrieval
- Handling of a claim that does not exist

Expected result:

```text
Tests run: 2
Failures: 0
Errors: 0
BUILD SUCCESS
```

---

## Swagger Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for viewing and testing the REST APIs.

---

## Build

To create the application build:

```bash
mvn clean package
```

The generated JAR file will be available inside:

```text
target/
```

---

## Conclusion

Expense Reviewer is a backend application that demonstrates the development of a production-style REST API using Spring Boot.

The project combines **REST APIs, MySQL, JPA/Hibernate, validation, policy-based expense checking, pagination, sorting, exception handling, unit testing, and Swagger documentation** into a single expense management system.
