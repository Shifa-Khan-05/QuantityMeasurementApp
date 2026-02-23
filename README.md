# Quantity Measurement App

## UC9 – Addition of Weight Measurement

### Overview

UC9 extends the Quantity Measurement App by introducing a new measurement category: **Weight**.

Until UC8, the system supported only **Length** measurements.
UC9 expands the architecture to support **multiple independent measurement categories** while preserving type safety and immutability.

---

## Objective

Introduce weight measurement support with:

* Equality comparison
* Unit conversion
* Addition operations
* Explicit target unit addition
* Category type safety

---

## Supported Weight Units

| Unit          | Base Conversion    |
| ------------- | ------------------ |
| Kilogram (kg) | Base unit          |
| Gram (g)      | 1 kg = 1000 g      |
| Pound (lb)    | 1 lb = 0.453592 kg |

---

## Features Implemented

### 1️⃣ Equality Comparison

Weight objects can be compared across units.

Examples:

* 1 kg == 1000 g
* 2.20462 lb == 1 kg

---

### 2️⃣ Unit Conversion

Supports conversion across all weight units:

* kg → g
* g → lb
* lb → kg

Round-trip conversion maintains precision.

---

### 3️⃣ Addition Operations

Two weights can be added:

* Result returned in first operand unit
* Result returned in explicitly specified target unit

Examples:

* 1 kg + 1000 g = 2 kg
* 1 kg + 1000 g (GRAM) = 2000 g

---

### 4️⃣ Category Type Safety

Length and Weight are independent categories.

Invalid comparisons are prevented.

Example:

* 1 kg ≠ 1 foot

Cross-category operations throw validation exceptions.

---

### 5️⃣ Immutability & Precision

* All operations return new objects
* No mutation of existing instances
* Floating-point precision maintained
* Supports zero, negative, and large values

---

## Architectural Impact

* Reusable enum-based conversion structure
* Separate measurement categories
* Clean extensibility model
* No breaking changes to Length functionality
* Preserves UC1–UC8 behavior

---

## Concepts Applied

* Multi-domain measurement modeling
* Enum-based conversion abstraction
* Category isolation
* Arithmetic on Value Objects
* Type safety enforcement
* Immutability principle

---

## Learning Outcome

UC9 demonstrates:

* Extending domain model safely
* Supporting multiple measurement categories
* Designing scalable conversion architecture
* Enforcing strict type safety across domains

---

## Branch Link

[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement)

---
