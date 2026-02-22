# Quantity Measurement App

## Branch: feature/UC4-AddNewUnits

---

# UC4 — Add New Units (Extensibility Proof)

## Goal

Demonstrate that the refactored design (introduced in UC3) is scalable and extensible by adding new measurement units without modifying core logic.

This use case validates adherence to the Open/Closed Principle.

---

## New Units Added

* YARDS
* CENTIMETERS

Only the `LengthUnit` enum was updated.
No changes were made to the `Length` class logic.

---

## Conversion Factors

| Unit         | Base Conversion (Inches) |
| ------------ | ------------------------ |
| 1 Foot       | 12                       |
| 1 Yard       | 36                       |
| 1 Inch       | 1                        |
| 1 Centimeter | 0.393701                 |

All comparisons continue to use the base unit conversion strategy.

---

## Tests Added

* Yard equals Yard
* Yard equals Feet
* Yard equals Inches
* Feet equals Yard (symmetry validation)
* Inches equals Yard (symmetry validation)
* Centimeter equals Inches
* Centimeter not equal to Feet
* Transitive property validation

All tests passed without modifying existing comparison logic.

---

## Design Validation

This confirms:

* The system is extensible
* New units can be added without changing core equality logic
* Base unit conversion strategy is stable
* DRY principle maintained

---

## Learning Outcome

* Applied Open/Closed Principle
* Verified extensible architecture
* Strengthened domain modeling
* Ensured scalability through clean abstraction

---
