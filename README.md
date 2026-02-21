# Quantity Measurement App

## Branch: feature/UC1-FeetEquality

Test Code Reference:
[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/blob/feature/UC1-FeetEquality/src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/blob/feature/UC1-FeetEquality/src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java)

---

# UC1 — Feet Equality (TDD Implementation)

## Goal

Implement equality comparison between two Feet measurements using Test-Driven Development (TDD).

This is the first incremental step toward building a scalable Quantity Measurement system.

---

# Development Approach

This feature strictly follows the TDD cycle:

1. Write a failing test
2. Write minimal code to pass the test
3. Refactor without breaking tests

All logic was test-driven before implementation.

---

# Tests Implemented

The equality contract was validated with the following cases:

* Same value should return true
* Different value should return false
* Null comparison should return false
* Comparison with different object type should return false
* Same reference should return true

---

# Implementation Details

Created a Feet class with:

* A value field
* Overridden equals() method
* Proper equality contract handling

The implementation ensures:

* Reflexive behavior
* Symmetric comparison
* Consistency
* Null safety
* Type safety

---

# Learning Outcomes

* Clear understanding of Java equals() contract
* Practical application of Test-Driven Development
* Writing minimal, clean implementation code
* Maintaining code safety through refactoring
* Following structured Git feature branch workflow

---
