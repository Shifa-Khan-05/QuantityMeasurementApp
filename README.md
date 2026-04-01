Secure Quantity Measurement System (UC18)

---

## 📌 Overview
This project is a robust, Spring Boot-based **Quantity Measurement System** that has evolved through an incremental **Test-Driven Development (TDD)** workflow. Use Case 18 (UC18) introduces a secure, stateless authentication architecture integrating **JWT** and **OAuth2 (Google/GitHub)**, ensuring high-level security while maintaining backward compatibility with the core measurement logic.

---

## 🛡️ Security Architecture
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
* **Enums:** Type-safe constants for `Role` (USER, ADMIN), `AuthProvider` (LOCAL, GOOGLE, GITHUB), and `OperationType`.
* **DTO Layer:** * **Requests:** `AuthRequest`, `RegisterRequest` (located in `dto/request/`).
    * **Responses:** `AuthResponse` (located in `dto/response/`).

---

## 🧪 Testing Suite (TDD)
The project maintains 100% functional integrity through comprehensive test coverage:
* **Unit Tests:** Validating JWT logic, User mapping, and DTO constraints.
* **Provider Tests:** Specific isolation tests for GitHub/Google authentication and persistence.
* **Integration Tests:** Verifying the synergy between Spring Security, JPA, and REST endpoints.
* **Legacy Support:** `QuantityMeasurementServiceIntegrationTest` ensures core measurement logic remains unaffected by security refactoring.

---

## ⚙️ Refactoring & Best Practices
* **Logging:** Migrated all classes to Lombok `@Slf4j` for consistent output.
* **Structure:** Clean separation of Request/Response DTOs and sub-packaged security components.
* **Configuration:** Multi-profile support via `application-dev`, `test`, and `prod` properties.
* **Security:** `.gitignore` updated to exclude `.env` and production secrets.

---

## 🚀 Development Workflow
1.  **Red:** Define expected behavior through failing tests.
2.  **Green:** Implement minimal code to satisfy requirements.
3.  **Refactor:** Clean and optimize while preserving existing behavior.
4.  **Evolve:** Incremental capability-based growth across measurement categories.

---
