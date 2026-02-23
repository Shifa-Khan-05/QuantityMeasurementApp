# Quantity Measurement App

## UC10 – Generic Quantity Measurement using Interface & Generics

### Overview

UC10 introduces a major architectural refactor by converting the system into a **generic, reusable measurement framework** using **interfaces and generics**.

The application now supports multiple measurement domains (Length and Weight) through a unified abstraction.

This significantly improves scalability, maintainability, and code reuse.

---

## Objective

Design a flexible measurement system that:

* Supports multiple unit categories
* Eliminates duplication
* Preserves type safety
* Maintains immutability
* Keeps backward compatibility

---

## What Was Implemented

### 1️⃣ Common Interface – `IMeasurable`

Created a shared interface to standardize unit behavior.

Responsibilities:

* convertToBaseUnit()
* convertFromBaseUnit()
* Unit name access

This enables any future unit type (Temperature, Volume, etc.) to integrate seamlessly.

---

### 2️⃣ Refactored Unit Enums

Both enums now implement `IMeasurable`:

* LengthUnit
* WeightUnit

Each unit defines:

* Base conversion factor
* Conversion logic

This centralizes conversion behavior inside the unit itself.

---

### 3️⃣ Generic Quantity Class

Introduced a reusable generic class:

```
Quantity<U extends IMeasurable>
```

Capabilities:

* Cross-unit equality comparison
* Unit conversion
* Addition (default result unit)
* Addition with explicit target unit
* Input validation
* Immutable design

This removes duplication across Length and Weight logic.

---

### 4️⃣ Multi-Domain Support

System now supports:

* Length conversions and arithmetic
* Weight conversions and arithmetic

Both domains share the same generic infrastructure.

---

### 5️⃣ Test Coverage

Added 30+ unit tests covering:

* Enum conversion logic
* Equality checks
* Conversion operations
* Addition operations
* Explicit target unit addition
* Null & invalid inputs
* HashCode consistency
* Immutability
* Backward compatibility

---

## Architectural Impact

* Generic domain modeling
* Strong type safety across categories
* Elimination of measurement-specific duplication
* Scalable design for future measurement types
* Clean separation of concerns

---

## Key Concepts Applied

* Generics with bounded types
* Interface-driven design
* Open/Closed Principle
* Immutability
* Domain abstraction
* Reusable arithmetic logic

---

## Learning Outcome

UC10 demonstrates:

* Advanced refactoring using TDD
* Generic architecture design
* Cross-domain extensibility
* Strong compile-time type safety
* Clean API design for measurement systems

---

## Branch Link

[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC10-GenericQuantity](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC10-GenericQuantity)

---
