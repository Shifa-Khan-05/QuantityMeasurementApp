# UC3 — Refactor to Generic Length Class

## Goal

Eliminate duplication between Feet and Inches classes by introducing a generic and extensible measurement model.

---

## Problem Identified

In UC1 and UC2:

* Feet and Inches had identical equality logic
* Code duplication violated the DRY principle
* Design was not scalable for adding new units

---

## Refactoring Performed

Removed:

* Feet class
* Inches class

Introduced:

* `Length` class
* `LengthUnit` enum

---

## Core Design Change

Instead of creating separate classes for each unit, we implemented a single generic model:

Length(value, LengthUnit)

This allows easy extension to additional units without duplicating logic.

---

## Base Unit Strategy

All measurements are internally converted to a common base unit: **Inches**

Conversion logic:

* FEET → 12 inches
* INCHES → 1 inch

Added method:

convertToBaseUnit()

Equality comparison now happens after converting both values to the base unit.

---

## Tests Covered

* Feet equals Feet
* Inches equals Inches
* 1 Foot equals 12 Inches
* Symmetry validation
* Transitive equality
* Complete equals contract validation

---

## Learning Outcome

* Safe refactoring using test coverage
* Designing scalable domain models
* Applying DRY principle
* Improving maintainability and extensibility
* Understanding base-unit conversion strategy

---
