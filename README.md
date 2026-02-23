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
