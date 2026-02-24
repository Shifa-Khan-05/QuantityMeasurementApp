# Quantity Measurement App

## UC14 — Temperature Measurement with Selective Arithmetic Support

### Branch: `feature/UC14-Temperature-Measurement`

Repository Link:
[https://github.com/Shifa-Khan-05/QuantityMeasurementApp](https://github.com/Shifa-Khan-05/QuantityMeasurementApp)

---

## Objective

Extend the Quantity Measurement Application to support **Temperature Units** while preserving clean architecture and SOLID principles.

Unlike Length, Weight, and Volume, temperature does **not support full arithmetic operations**. This use case refactors the system to allow **selective arithmetic capability per measurement category**.

---

## Key Enhancements

### 1. Added TemperatureUnit Enum

Supported Units:

* CELSIUS
* FAHRENHEIT
* KELVIN

Implemented:

* Accurate non-linear conversion formulas
* Cross-unit equality
* Override for unsupported arithmetic operations

---

### 2. Refactored IMeasurable Interface

Enhanced interface with:

* Default methods for optional arithmetic support
* `validateOperationSupport()` method
* `supportsArithmetic()` capability check
* Functional Interface: `SupportsArithmetic`
* Lambda expressions for capability declaration

This ensures:

* Backward compatibility (UC1–UC13 remain unchanged)
* Interface Segregation Principle compliance
* Non-breaking interface evolution

---

### 3. Selective Arithmetic Support

| Category    | Addition | Subtraction | Division |
| ----------- | -------- | ----------- | -------- |
| Length      | Yes      | Yes         | Yes      |
| Weight      | Yes      | Yes         | Yes      |
| Volume      | Yes      | Yes         | Yes      |
| Temperature | ❌ No     | ❌ No        | ❌ No     |

Temperature operations now throw:

`UnsupportedOperationException`

With clear error messages.

---

### 4. Temperature Conversion Formulas

* °F = (°C × 9/5) + 32
* °C = (°F − 32) × 5/9
* K = °C + 273.15

Edge cases handled:

* Absolute zero
* -40°C = -40°F
* Precision tolerance (epsilon-based equality)

---

## Architectural Improvements

* Interface Segregation Principle (ISP)
* Capability-based design
* Default methods in interfaces
* Lambda expressions
* Functional interfaces
* Non-linear unit conversion handling
* Polymorphic error messaging
* Generic type safety preserved

---

## Type Safety & Cross-Category Protection

Temperature cannot be compared with:

* Length
* Weight
* Volume

Generics + runtime checks prevent category mixing.

---

## Testing Coverage

* Cross-unit temperature equality
* Conversion accuracy
* Symmetry & transitive equality
* Unsupported operation validation
* Cross-category comparison prevention
* Backward compatibility with UC1–UC13
* Precision tolerance validation
* Edge case testing

All previous test cases pass without modification.

---

## Learning Outcomes

* Designing extensible generic systems
* Evolving interfaces safely
* Handling non-linear conversions
* Applying Interface Segregation Principle
* Capability-based API design
* Advanced enum behavior with lambdas
* Clean exception semantics
* Preserving backward compatibility in large systems

---
