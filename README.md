Spring Backend for Quantity Measurement (UC17)

### 📌 Project Overview
This module (UC17) represents the transition of the **Quantity Measurement System** into a professional, enterprise-grade backend using the **Spring Boot Framework**. It moves beyond core Java logic to provide a scalable RESTful API, enabling seamless communication between a client-side interface and the measurement service.

---

### 🏗️ Core Spring Concepts Applied

The backend architecture is built upon the following fundamental Spring pillars:

* **Spring Core & IoC:** Utilizes **Inversion of Control (IoC)** and **Dependency Injection (DI)** to manage the lifecycle of measurement services and units, ensuring loose coupling.
* **Spring Boot:** Leverages auto-configuration and an embedded Tomcat server to simplify the deployment and setup of the measurement application.
* **Spring MVC:** Implements the Model-View-Controller design pattern to separate the internal measurement logic (Model) from the API endpoints (Controller).

---

### 🌐 REST API Implementation

The system exposes a set of RESTful endpoints to perform unit conversions and quantity comparisons.

#### **Key Components:**
* **Spring Controller:** Handles incoming HTTP requests (GET/POST) and maps them to specific service methods.
* **Request/Response DTOs:** Ensures type-safe data transfer between the client and the server.
* **REST Annotations:** Uses `@RestController`, `@PostMapping`, and `@RequestBody` to define a modern API contract.

---

### 🛠️ Technology Stack
* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Build Tool:** Maven/Gradle
* **API Protocol:** REST (JSON)
* **Testing:** JUnit 5 & Mockito (TDD approach)

---

### ⚙️ Development Workflow (UC17 Focus)
1.  **Bootstrap:** Initializing the project with Spring Initializr and required dependencies (Spring Web, Lombok).
2.  **Service Integration:** Porting the existing `QuantityMeasurementService` into a Spring-managed `@Service` bean.
3.  **Controller Layer:** Developing the `MeasurementController` to expose endpoints for Length, Volume, and Weight conversions.
4.  **Validation:** Implementing Spring-based validation to ensure input units are compatible (e.g., preventing a conversion from "Litre" to "Kilogram").
5.  **Integration Testing:** Using `MockMvc` to verify the API response codes ($200\text{ OK}$, $400\text{ Bad Request}$) and JSON payloads.

---
