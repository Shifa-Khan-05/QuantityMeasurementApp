# Quantity Measurement App

## Branch: feature/UC5-UnitConversion

---

# UC5 — Unit Conversion

## Goal

Extend the application to support explicit unit conversion between different measurement units.

Until UC4, the system could compare measurements across units using base-unit conversion.
UC5 introduces a formal conversion API for direct unit transformation.

---

## Features Added

### 1. Static Conversion Method

```
convert(value, fromUnit, toUnit)
```

Allows conversion without creating an object.

---

### 2. Instance Conversion Method

```
length.convertTo(targetUnit)
```

Converts an existing Length object to another unit.

---

### 3. Helper / Overloaded Methods

Added utility overloads to simplify usage and improve API clarity.

---

## Conversion Strategy

All conversions are still based on the internal base unit (Inches).

Flow:

1. Convert source value to base unit
2. Convert base unit to target unit

This ensures consistent and scalable conversion logic.

---

## Test Coverage

* Feet ↔ Inches conversion
* Yards ↔ Inches conversion
* Centimeters ↔ Inches conversion
* Zero value handling
* Negative value handling
* Round-trip conversion validation
* Null and NaN input validation

All previous tests remained green, ensuring backward compatibility.

---

## Design Impact

* No duplication introduced
* Conversion logic reused base-unit design from UC3
* Maintains Open/Closed Principle
* Fully test-driven implementation

---

## Learning Outcome

* Designing clean and reusable APIs
* Extending domain model safely
* Handling edge cases and validations
* Building scalable conversion architecture

---
