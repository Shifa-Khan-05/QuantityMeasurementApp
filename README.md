<<<<<<< HEAD
# Secure Quantity Measurement System (UC1–UC18)

## 📌 Overview
This project is a robust, Spring Boot-based **Quantity Measurement System** that has evolved through an incremental **Test-Driven Development (TDD)** workflow. Use Case 18 (UC18) introduces a secure, stateless authentication architecture integrating **JWT** and **OAuth2 (Google/GitHub)**, ensuring high-level security while maintaining backward compatibility with the core measurement logic.

---

## 🛡️ Security Architecture (UC18)
The system employs a layered security structure within the `security/` package:

### **JWT Authentication (`security/jwt/`)**
* **JwtTokenProvider:** Handles generation, parsing, validation, and claim extraction.
* **JwtAuthenticationFilter:** Intercepts HTTP requests to establish the security context.
* **Handlers:** Includes `JwtAuthenticationEntryPoint` (401 Unauthorized) and `JwtAccessDeniedHandler` (403 Forbidden).

### **OAuth2 Integration (`security/oauth2/`)**
* **CustomOAuth2UserService:** Maps social profiles (Google/GitHub) to application users.
* **Success/Failure Handlers:** Manages JWT generation post-login or handles authentication errors.

---

## 🏗️ Domain & Data Model
* **Identity:** `User` entity with `UserRepository` (JPA) supporting email and provider-based lookups.
* **Enums:** Type-safe constants for `Role` (USER, ADMIN), `AuthProvider` (LOCAL, GOOGLE, GITHUB).
* **DTO Layer:** * **Requests:** `AuthRequest`, `RegisterRequest` (located in `dto/request/`).
    * **Responses:** `AuthResponse` (located in `dto/response/`).

---

## 🧪 Testing Suite (TDD)
The project maintains 100% functional integrity through comprehensive test coverage:
* **Unit Tests:** Validating JWT logic, User mapping, and DTO constraints.
* **Integration Tests:** Verifying synergy between Spring Security, JPA, and REST endpoints.
* **Legacy Support:** `QuantityMeasurementServiceIntegrationTest` ensures core measurement logic (UC1-UC17) remains unaffected.

---

## ⚙️ Development Workflow
1. **Red:** Define expected behavior through failing tests.
2. **Green:** Implement minimal code to satisfy requirements.
3. **Refactor:** Clean and optimize while preserving existing behavior.
4. **Evolve:** Incremental capability-based growth across measurement categories.

---

# 📜 Project Evolution (History)

<details>
<summary>Click to view Use Cases 1–17 details</summary>

### UC1 — UC5: Foundations & Refactoring
* **UC1-UC2:** Initial TDD for Feet and Inches equality.
* **UC3-UC5:** Refactored to a generic `Length` model with unit conversion (Yards, CM) and a centralized `convertToBaseUnit` strategy.

### UC6 — UC9: Arithmetic & Weight
* **UC6-UC7:** Addition support with explicit target units.
* **UC8-UC9:** Refactored enums and introduced **Weight** measurement (KG, Grams, Pounds).

### UC10 — UC12: Generics & Volume
* **UC10-UC11:** Introduced `IMeasurable` interface and Generic `Quantity<U>` class. Added **Volume** (Litres, ML, Gallons).
* **UC12:** Implemented Subtraction and Division (dimensionless ratio).

### UC13 — UC14: DRY Refactor & Temperature
* **UC13:** Centralized arithmetic logic using the `ArithmeticOperation` enum.
* **UC14:** Added **Temperature** (Celsius, Fahrenheit, Kelvin) with non-linear formulas and restricted arithmetic support.

### UC15 — UC17: N-Tier & Persistence
* **UC15:** Refactored into Application, Controller, Service, and Repository layers.
* **UC16:** Database integration using JDBC and Connection Pooling.
* **UC17:** Spring Boot Integration and REST API development.

</details>

---
*Developed by [Shifa Khan] - 2026*
>>>>>>> dev

---
