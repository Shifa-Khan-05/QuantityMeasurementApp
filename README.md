# Quantity Measurement App

## Project Overview

The Quantity Measurement App is a Test-Driven Development (TDD) based project designed to build scalable and maintainable measurement comparison logic step by step.

The system currently supports equality comparison for multiple length units while following:

* Test Driven Development (TDD)
* Incremental Development
* Clean Code Principles
* DRY (Don't Repeat Yourself)
* Structured Git Workflow

Development progresses through small, focused Use Cases (UCs), ensuring maintainability and extensibility.

---

## Development Methodology

This project follows the TDD cycle:

1. Write failing tests
2. Write minimal code to pass
3. Refactor safely

This guarantees:

* Safety
* Maintainability
* Controlled growth of complexity

---

## Git Workflow

* main → stable production code
* dev → integration branch
* feature/UCx-* → feature-specific branches

Each use case was developed independently and merged after validation.

---

# Use Case Implementation

## UC1 — Feet Equality

### Goal

Compare two Feet measurements for equality.

### Tests Implemented

Validated the equals contract:

* Same value → equal
* Different value → not equal
* Null comparison → false
* Different object type → false
* Same reference → true

### Implementation

* Created `Feet` class
* Added `value` field
* Overridden `equals()` method

### Learning

* Strong understanding of equality contract
* First step into TDD-based design

---

## UC2 — Inches Equality

### Goal

Add support for Inches unit comparison.

### Tests Implemented

Repeated equality validation for:

* Inches = Inches

### Implementation

* Created `Inches` class
* Added `value` field
* Overridden `equals()` method

### Design Observation

* Identified duplicated logic between Feet and Inches
* Violated DRY principle
* Recognized need for abstraction in upcoming refactoring

---

## Current System Capabilities

The application can now:

* Compare Feet measurements
* Compare Inches measurements
* Safely validate equality contracts
* Maintain full test coverage

---

## Learning Outcomes

* Practical implementation of TDD
* Equality contract validation
* Incremental feature growth
* Early detection of design smells
* Foundation ready for abstraction in next UC

---

## Refactored Architecture & Conversion Support (UC3–UC5)

This phase of the project focuses on refactoring, extensibility, and explicit unit conversion while maintaining full Test-Driven Development (TDD) discipline.

The design evolved from unit-specific classes to a scalable and extensible measurement system.

---

# UC3 — Refactor to Generic Length Model

## Goal

Eliminate duplication and introduce a generic, scalable design.

## Refactoring Performed

Removed:

* Feet class
* Inches class

Introduced:

* `Length` class
* `LengthUnit` enum

## Core Design

Instead of separate classes for each unit:

Length(value, LengthUnit)

## Base Unit Strategy

All measurements are internally converted to a common base unit: **Inches**

Conversion rules:

* FEET → 12 inches
* INCHES → 1 inch

Added:

convertToBaseUnit()

## Tests Covered

* Feet equals Feet
* Inches equals Inches
* 1 Foot equals 12 Inches
* Symmetry validation
* Transitive equality
* Full equals contract validation

## Learning

* Safe refactoring using tests
* Generic domain modeling
* DRY principle implementation

---

# UC4 — Extensibility with New Units

## Goal

Validate that the architecture supports adding new units without modifying core logic.

## Units Added

* YARDS
* CENTIMETERS

Only the `LengthUnit` enum was updated.
No changes were made to equality logic.

## Conversion Factors

| Unit         | Inches Equivalent |
| ------------ | ----------------- |
| 1 Foot       | 12                |
| 1 Yard       | 36                |
| 1 Inch       | 1                 |
| 1 Centimeter | 0.393701          |

## Tests Added

* Yard equals Yard
* Yard equals Feet
* Yard equals Inches
* Feet equals Yard
* Inches equals Yard
* Centimeter equals Inches
* Centimeter not equal to Feet
* Transitive validation

## Learning

* Applied Open/Closed Principle
* Confirmed scalable architecture
* Extended system without modifying existing logic

---

# UC5 — Explicit Unit Conversion

## Goal

Add direct unit conversion capability.

Until UC4, the system only supported equality comparison.
UC5 introduces a formal conversion API.

## Features Added

### Static Conversion Method

convert(value, fromUnit, toUnit)

### Instance Conversion Method

length.convertTo(targetUnit)

### Helper Methods

Overloaded utilities for cleaner API usage.

## Conversion Strategy

1. Convert source value to base unit (Inches)
2. Convert base unit to target unit

Ensures consistent and reusable logic.

## Test Coverage

* Feet ↔ Inches conversion
* Yards ↔ Inches conversion
* Centimeters ↔ Inches conversion
* Zero and negative values
* Round-trip conversion
* Null and NaN validation

All previous tests remain green.

## Learning

* Clean API design
* Reusable architecture
* Safe feature extension
* Strong edge-case validation

---

# Current System Capabilities

The application now supports:

* Cross-unit equality comparison
* Base-unit normalization
* Extensible unit addition
* Explicit unit conversion API
* Full test-driven safety

---

# UC6 — Addition of Two Length Units (Same Category)

## Goal

Extend the Quantity Measurement API to support addition between two length measurements, even if they belong to different units, while returning the result in the unit of the first operand.

This use case builds on UC5’s conversion infrastructure.

---

## Core Feature

Add two `Length` objects:

QuantityLength.add(length1, length2)

or

length1.add(length2)

Result:

* Returned in the unit of the first operand
* Original objects remain unchanged (immutability preserved)

---

## Implementation Strategy

### Validation

* Both operands must be non-null
* Units must be valid
* Values must be finite (not NaN or infinite)

### Conversion Flow

1. Convert both operands to base unit (Feet)
2. Perform addition
3. Convert result back to unit of first operand
4. Return new Length instance

---

## Key Concepts Applied

* Arithmetic operations on value objects
* Reuse of base-unit conversion strategy
* Immutability principle
* Open/Closed compliance
* Commutativity validation
* Identity element validation
* Floating-point precision handling

---

## Test Coverage

### Same Unit Addition

* 1 Foot + 2 Feet = 3 Feet
* 6 Inches + 6 Inches = 12 Inches

### Cross-Unit Addition

* 1 Foot + 12 Inches = 2 Feet
* 12 Inches + 1 Foot = 24 Inches
* 1 Yard + 3 Feet = 2 Yards
* 2.54 cm + 1 Inch ≈ 5.08 cm

### Mathematical Properties

* Commutativity (A + B = B + A)
* Identity (adding zero returns same value)
* Negative values supported
* Large & small magnitude validation

### Error Handling

* Null operand throws exception
* Invalid unit handling
* NaN and infinite validation

---

## Example Outputs

add(Quantity(1.0, FEET), Quantity(12.0, INCHES))
→ Quantity(2.0, FEET)

add(Quantity(12.0, INCHES), Quantity(1.0, FEET))
→ Quantity(24.0, INCHES)

add(Quantity(5.0, FEET), Quantity(0.0, INCHES))
→ Quantity(5.0, FEET)

---

## Learning Outcomes

* Extending domain model with arithmetic operations
* Leveraging abstraction for reuse
* Maintaining immutability
* Handling precision in floating-point arithmetic
* Designing mathematically consistent APIs

---
