# Quantity Measurement App

## UC13 – Centralized Arithmetic Logic (DRY Refactor)

### Branch: `feature/UC13-Centralized-Arithmetic-Logic`

Project Link:
[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC13-Centralized-Arithmetic-Logic](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC13-Centralized-Arithmetic-Logic)

---

## Objective

Refactor arithmetic operations (add, subtract, divide) introduced in UC12 to eliminate code duplication and enforce the **DRY principle**.

Public API remains unchanged.
All UC12 behaviors are preserved.
All existing test cases pass without modification.

---

## Problem in UC12

UC12 had repeated logic in:

* add()
* subtract()
* divide()

Each method repeated:

* Null validation
* Category compatibility checks
* Finiteness checks
* Base-unit conversion
* Target unit handling

This violated:

* DRY principle
* Maintainability standards
* Scalability for future operations

---

## Refactoring Strategy

### 1. Introduced `ArithmeticOperation` Enum

Enum-based operation dispatch:

* ADD
* SUBTRACT
* DIVIDE

Each constant defines computation logic via:

* Abstract method implementation
  or
* Lambda expression using `DoubleBinaryOperator`

This replaces if-else / switch logic.

---

### 2. Centralized Validation Helper

```java id="y12a7k"
private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired)
```

Handles:

* Null operand checks
* Null target unit checks
* Category compatibility
* Finiteness validation
* Consistent error messages

Single source of truth for validation.

---

### 3. Centralized Arithmetic Helper

```java id="l82m1r"
private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation)
```

Handles:

* Base-unit conversion
* Operation execution via enum
* Division-by-zero check
* Returns base-unit result

---

### 4. Refactored Public Methods

Public methods now delegate:

* add() → helper + conversion
* subtract() → helper + conversion
* divide() → helper only (returns scalar)

Each method is now shorter, clearer, and focused.

---

## Behavior Preservation

All UC12 behaviors remain unchanged:

* Cross-unit arithmetic
* Explicit & implicit target units
* Division returns dimensionless double
* Non-commutative subtraction & division
* Immutability preserved
* Rounding applied to add/subtract only
* Cross-category prevention intact

All UC12 test cases pass without modification.

---

## Architectural Improvements

* DRY principle fully enforced
* Single validation logic
* Single conversion logic
* Enum-based operation dispatch
* Reduced code duplication
* Cleaner method readability
* Scalable for future operations (Multiply, Modulo, etc.)
* Private helper encapsulation

---

## Scalability Validation

Adding a new operation now requires:

1. Add enum constant in `ArithmeticOperation`
2. No changes to validation
3. No duplication of conversion logic

Architecture now supports unlimited arithmetic extensions.

---

## Learning Outcomes

* Advanced refactoring techniques
* Enum-based polymorphism
* Lambda expressions & functional interfaces
* Centralized validation strategy
* Operation dispatch without condition chains
* Clean API preservation during internal restructuring
* Behavior-preserving refactor
* SOLID principle reinforcement

---
