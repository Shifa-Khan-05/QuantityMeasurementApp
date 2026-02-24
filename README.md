# Quantity Measurement App

## UC11 – Volume Measurement Equality, Conversion, and Addition

### Branch: `feature/UC11-VolumeMeasurement`

Project Link:
[https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC11-VolumeMeasurement](https://github.com/Shifa-Khan-05/QuantityMeasurementApp/tree/feature/UC11-VolumeMeasurement)

---

## Objective

Extend the generic architecture introduced in UC10 to support a third measurement category: **Volume**.

Supported units:

* LITRE (Base Unit)
* MILLILITRE (1 L = 1000 mL)
* GALLON (1 gal ≈ 3.78541 L)

No changes were made to:

* `Quantity<U>`
* `IMeasurable`
* `QuantityMeasurementApp`
* Existing test infrastructure

This validates that the architecture scales seamlessly.

---

## Implementation Details

### 1. Created `VolumeUnit` Enum

`VolumeUnit` implements `IMeasurable` and defines:

* getConversionFactor()
* convertToBaseUnit(double value)
* convertFromBaseUnit(double baseValue)
* getUnitName()

Conversion factors (base unit: LITRE):

* LITRE → 1.0
* MILLILITRE → 0.001
* GALLON → 3.78541

---

### 2. Equality Support

Verified:

* 1 L = 1000 mL
* 1 gallon ≈ 3.78541 L
* Symmetry & transitive properties
* Cross-category comparison returns false

---

### 3. Unit Conversion

Tested:

* L → mL
* mL → L
* L → gallon
* gallon → L
* Round-trip conversions
* Zero, negative, and large values

---

### 4. Addition Operations

Supported:

* Same-unit addition
* Cross-unit addition
* Explicit target unit specification
* Commutativity
* Precision handling

Examples:

* 1 L + 1000 mL → 2 L
* 3.78541 L + 1 gallon → ~2 gallons
* Explicit conversion to millilitre or gallon

---

### 5. Category Isolation

Confirmed:

* Volume cannot be compared with Length
* Volume cannot be compared with Weight
* Generic type safety enforced at compile time
* Runtime safety check in equals()

---

## Key Architectural Validation

UC11 proves:

* Generic `<U extends IMeasurable>` design scales linearly
* New measurement categories require only a new enum
* DRY principle maintained
* Open/Closed Principle achieved
* Zero modification to core engine

Architecture is now validated for future categories such as:

* Temperature
* Time
* Area
* Speed

---

## Test Coverage

Covered:

* Litre ↔ Litre
* Millilitre ↔ Millilitre
* Gallon ↔ Gallon
* Cross-unit equality
* Conversion accuracy
* Addition (implicit & explicit unit)
* Null handling
* Precision tolerance
* Backward compatibility (UC1–UC10)

All previous use cases remain fully functional.

---

## Learning Outcome

* Scalable generic architecture
* Enum-based polymorphism
* Interface-driven design
* Floating-point precision management
* Category-safe generics
* Immutable quantity model

---
