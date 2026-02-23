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

# Quantity Measurement App

## UC7 – Addition with Explicit Target Unit Specification

### Overview

UC7 extends the addition functionality introduced in UC6 by allowing the caller to explicitly specify the target unit for the result.

Instead of defaulting the result to the unit of the first operand, UC7 provides:

add(length1, length2, targetUnit)

This ensures greater flexibility, clarity, and API consistency.

Example:

add(1 FEET, 12 INCHES, YARDS) → ~0.667 YARDS

---

## Objective

Provide explicit control over the unit in which the addition result should be expressed.

---

## Preconditions

* Length class exists (from UC3–UC6)
* LengthUnit enum includes:

  * FEET
  * INCHES
  * YARDS
  * CENTIMETERS
* All units share a consistent base unit (FEET)
* Inputs are valid Length objects
* Target unit is explicitly provided

---

## Core Flow

1. Validate:

   * length1 and length2 are non-null
   * targetUnit is non-null
   * Values are finite numbers
   * Units belong to same measurement category

2. Convert both operands to base unit (FEET)

3. Add base values

4. Convert result to explicitly specified targetUnit

5. Return new immutable Length object

---

## Postconditions

* Result is always returned in the specified target unit
* Original operands remain unchanged (immutability preserved)
* Addition remains commutative
* Invalid inputs throw IllegalArgumentException
* Accuracy maintained within floating-point tolerance

---

## Example Results

add(1 FEET, 12 INCHES, FEET) → 2 FEET
add(1 FEET, 12 INCHES, INCHES) → 24 INCHES
add(1 FEET, 12 INCHES, YARDS) → ~0.667 YARDS
add(2 YARDS, 3 FEET, FEET) → 9 FEET
add(5 FEET, -2 FEET, INCHES) → 36 INCHES

---

## Concepts Applied

* Method Overloading (implicit + explicit addition)
* Explicit parameter passing
* Base-unit conversion strategy
* DRY principle via private utility method
* API consistency
* Functional programming style (pure method behavior)
* Immutability and thread-safety
* Commutative property validation
* Precision handling with epsilon comparison

---

## Key Test Coverage

* Explicit target same as first operand
* Explicit target same as second operand
* Explicit target different from operands
* Cross-scale conversions (large → small, small → large)
* Zero and negative values
* Null target unit validation
* Commutativity with explicit target
* Precision tolerance across conversions
* All unit combination coverage

---

## Learning Outcome

UC7 demonstrates:

* Flexible API design
* Caller intent clarity
* Scalable arithmetic abstraction
* Clean extension without breaking previous UCs
* Strong validation discipline
* Unit-independent arithmetic operations

---

## Branch Link

[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC7-AdditionWithTargetUnit](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC7-AdditionWithTargetUnit)

---

## UC8 – Refactoring LengthUnit to Standalone Enum

### Overview

UC8 focuses on architectural refactoring by extracting the `LengthUnit` enum from inside the `Length` class and making it a standalone top-level enum.

This improves separation of concerns and aligns the design with the Single Responsibility Principle (SRP).

All functionality from UC1 through UC7 continues to work without modification.

---

## Objective

Refactor the design to:

* Separate unit behavior from measurement logic
* Improve scalability for future measurement categories
* Maintain backward compatibility
* Preserve all existing functionality

---

## What Was Refactored

### Before UC8

* `LengthUnit` enum was nested inside the `Length` class
* Conversion logic partially handled inside `Length`

### After UC8

* `LengthUnit` moved to its own file (standalone enum)
* All conversion logic moved into `LengthUnit`
* `Length` now delegates conversion responsibilities
* Circular dependency risk removed

---

## Design Improvements

### Separation of Responsibilities

* `Length` → Handles:

  * equality
  * conversion delegation
  * addition (UC6 & UC7)
* `LengthUnit` → Handles:

  * convertToBaseUnit()
  * convertFromBaseUnit()

---

## Benefits Achieved

* Cleaner architecture
* Better SRP compliance
* Easier to extend for:

  * Weight
  * Volume
  * Temperature
* Improved readability & maintainability
* No breaking changes
* All previous UC tests pass successfully

---

## Technical Highlights

* No API changes
* No behavior changes
* Full backward compatibility
* Improved modular design
* Enhanced scalability

---

## Learning Outcome

UC8 demonstrates:

* Refactoring with safety using TDD
* Architectural evolution without breaking functionality
* Clean separation of domain logic
* Preparing codebase for multi-measurement support

---

## Branch Link

[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC8-Refactor-LengthUnit](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC8-Refactor-LengthUnit)

---

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
