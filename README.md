# Quantity Measurement App

## Branch: feature/UC2-InchesEquality

---

# UC2 — Inches Equality (TDD Implementation)

## Goal

Extend the system to support **Inches** unit comparison, in addition to Feet.

This use case builds upon UC1 and continues the incremental development approach using Test-Driven Development (TDD).

---

## Development Approach

Followed the TDD cycle:

1. Write failing tests for Inches equality
2. Write minimal implementation to pass tests
3. Refactor while keeping tests green

---

## Tests Implemented

Validated equality behavior for Inches:

* Same value → should return true
* Different value → should return false
* Null comparison → should return false
* Different object type → should return false
* Same reference → should return true

---

## Implementation Details

Created an `Inches` class with:

* value field
* overridden `equals()` method

The logic mirrors the Feet implementation from UC1.

---

## Design Observation

Identified major code duplication:

* Feet and Inches classes contain identical equality logic
* Violates DRY principle

This highlighted the need for abstraction in future use cases.

---

## Learning Outcomes

* Extended system using incremental development
* Reinforced understanding of equality contract
* Identified design improvement opportunity
* Recognized importance of refactoring in TDD

---
