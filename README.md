# Quantity Measurement App

## UC12 – Subtraction and Division Operations

### Branch: `feature/UC12-Subtraction-Division`

Project Link:
[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC12-Subtraction-Division](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC12-Subtraction-Division)

---

## Objective

Extend the generic `Quantity<U>` architecture to support:

* Subtraction between quantities
* Division between quantities (dimensionless ratio)

This enhancement builds on UC1–UC11 (equality, conversion, addition) without modifying the core architecture.

---

## Features Implemented

### 1. Subtraction

Added two overloaded methods:

```java
subtract(Quantity<U> other)
subtract(Quantity<U> other, U targetUnit)
```

Behavior:

* Converts both operands to base unit
* Performs subtraction
* Converts result to implicit (first operand) or explicit target unit
* Rounds to two decimal places
* Returns new immutable `Quantity<U>`

Supported:

* Same-unit subtraction
* Cross-unit subtraction (within same category)
* Explicit target unit specification
* Negative results
* Zero results
* Chained subtraction

Cross-category subtraction throws `IllegalArgumentException`.

---

### 2. Division

Added:

```java
divide(Quantity<U> other)
```

Behavior:

* Converts operands to base unit
* Divides base values
* Returns dimensionless `double`
* Prevents division by zero (throws `ArithmeticException`)

Supported:

* Same-unit division
* Cross-unit division (within same category)
* Ratio > 1
* Ratio < 1
* Ratio = 1
* Large and small magnitude values

Cross-category division throws `IllegalArgumentException`.

---

## Mathematical Properties Verified

* Subtraction is non-commutative
* Division is non-commutative
* Division is non-associative
* Addition/subtraction inverse relationship validated
* Immutability preserved

---

## Validation & Error Handling

Handled:

* Null operands
* Null target units
* NaN / infinite values
* Division by zero
* Cross-category operations
* Precision rounding (subtraction only)

---

## Scalability

Subtraction and division work across:

* Length
* Weight
* Volume

No architectural changes were required.

The generic `<U extends IMeasurable>` design continues to scale cleanly.

---
