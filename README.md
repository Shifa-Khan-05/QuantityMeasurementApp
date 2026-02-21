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
