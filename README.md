# Quantity Measurement Application

## Overview

The **Quantity Measurement Application** is a scalable and extensible system developed using **Test-Driven Development (TDD)** principles. It supports multiple measurement categories such as **Length, Weight, Volume, and Temperature**, along with advanced arithmetic operations and secure authentication.

The project has evolved incrementally through multiple use cases (UC1–UC18), ensuring clean architecture, maintainability, and extensibility.

---

## Key Features

### Measurement Capabilities

* Cross-unit equality comparison
* Unit conversion across categories
* Arithmetic operations:

  * Addition
  * Subtraction
  * Division
* Explicit target unit operations
* Multi-category support:

  * Length
  * Weight
  * Volume
  * Temperature (conversion only)

### Architecture

* Generic design using `Quantity<U extends IMeasurable>`
* N-Tier Architecture:

  * Controller Layer
  * Service Layer
  * Repository Layer
  * Model/Entity Layer
* Clean separation of concerns
* DRY and SOLID principles applied

### Security (UC18)

* JWT-based authentication
* OAuth2 login (Google & GitHub)
* Stateless session management
* Role-based access control

### Persistence

* JDBC integration
* H2 database (default)
* Pluggable repository (Cache / Database)

---

## Technology Stack

* Java (Core + Generics)
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* OAuth2 (Google, GitHub)
* JDBC
* H2 Database
* Maven
* JUnit (TDD approach)

---

## Development Approach

This project strictly follows the **TDD cycle**:

1. Write failing test cases
2. Implement minimal code
3. Refactor safely

This ensures:

* High reliability
* Clean design evolution
* Strong test coverage

---

## System Architecture

```
Application Layer
        │
Controller Layer
        │
Service Layer
        │
Repository Layer
        │
Entity / Model Layer
```

---

## Project Evolution (Highlights)

* **UC1–UC2:** Basic equality (Feet, Inches)
* **UC3–UC5:** Generic Length model + conversion
* **UC6–UC7:** Addition operations
* **UC8–UC10:** Refactoring + Generic architecture
* **UC9–UC11:** Multi-category support (Weight, Volume)
* **UC12–UC13:** Arithmetic operations + DRY refactor
* **UC14:** Temperature (non-linear conversion)
* **UC15:** N-Tier architecture
* **UC16:** JDBC database integration
* **UC18:** Security with JWT & OAuth2

---

## Security Architecture

### JWT

* Token generation and validation
* Request filtering
* Secure API access

### OAuth2

* Google and GitHub login
* Custom user mapping
* Success & failure handlers

---

## Testing

* Unit Tests
* Integration Tests
* Security Tests
* Repository & Service Tests
* Full backward compatibility maintained across all use cases

---

## How to Run

1. Clone the repository:

```
git clone <repository-url>
```

2. Navigate to main branch:

```
git checkout main
```

3. Run the application:

```
mvn spring-boot:run
```

---

## Design Principles

* SOLID Principles
* DRY (Don't Repeat Yourself)
* Immutability
* Generic Programming
* Interface Segregation
* Open/Closed Principle

---

## Future Enhancements

* UI Integration (React frontend)
* Cloud deployment
* Microservices architecture
* Advanced analytics on measurement history

---
