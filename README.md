# Quantity Measurement App

## Branch: feature/UC6-AdditionOfLengths

🔗 Reference Document: 

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
