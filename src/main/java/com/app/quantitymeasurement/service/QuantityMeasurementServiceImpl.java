package com.app.quantitymeasurement.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.core.*;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.UserRepository;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // ================= HELPERS (AUTH & PERSISTENCE) =================

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        // Assuming auth.getName() returns the email from JWT
        return userRepository.findByEmail(auth.getName()).orElse(null);
    }

    private void saveToHistory(String op, Quantity<?> q1, Quantity<?> q2, String res) {
        User user = getAuthenticatedUser();
        if (user != null) {
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setUser(user);
            entity.setOperation(op);
            entity.setOperand1(q1.toString());
            entity.setOperand2(q2 != null ? q2.toString() : "N/A");
            entity.setResult(res);
            repository.save(entity);
        }
    }

    // ================= UNIT NORMALIZATION =================

    private String normalizeUnit(String unit) {

        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be null or empty");
        }

        String normalized = unit.trim().toLowerCase();

        return switch (normalized) {

            // LENGTH
            case "meter", "meters", "metre", "metres", "m" -> "METERS";
            case "centimeter", "centimeters", "centimetre", "centimetres", "cm" -> "CENTIMETERS";
            case "feet", "foot", "ft" -> "FEET";
            case "inch", "inches", "in" -> "INCHES";
            case "yard", "yards", "yd" -> "YARDS";

            // TEMPERATURE
            case "celsius", "celcius", "c" -> "CELSIUS";
            case "fahrenheit", "f" -> "FAHRENHEIT";
            case "kelvin", "k" -> "KELVIN";

            // WEIGHT
            case "gram", "grams", "g" -> "GRAM";
            case "kilogram", "kilograms", "kg", "kgs" -> "KILOGRAM";
            case "milligram", "milligrams", "mg" -> "MILLIGRAM";
            case "pound", "pounds", "lb", "lbs" -> "POUND";
            case "tonne", "tonnes" -> "TONNE";

            // VOLUME
            case "litre", "litres", "liter", "liters", "l" -> "LITRE";
            case "millilitre", "millilitres", "milliliter", "milliliters", "ml" -> "MILLILITRE";
            case "gallon", "gallons", "gal" -> "GALLON";

            default -> throw new IllegalArgumentException("Unsupported Unit: " + unit);
        };
    }
    
    
    //====================
    private Quantity<?> createQuantity(QuantityDTO dto) {
        String normalizedUnit = normalizeUnit(dto.getUnit());
        try { return new Quantity<>(dto.getValue(), LengthUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), WeightUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), VolumeUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), TemperatureUnit.valueOf(normalizedUnit)); } catch (Exception ignored) {}
        throw new IllegalArgumentException("Unsupported Unit Type: " + dto.getUnit());
    }

    // ================= VALIDATIONS =================

    private void validateSameType(Quantity<?> q1, Quantity<?> q2) {
        if (!q1.getUnit().getClass().equals(q2.getUnit().getClass())) {
            throw new IllegalArgumentException("Cannot operate on different unit types");
        }
    }

    private void validateTemperature(Quantity<?> q1, Quantity<?> q2, String op) {
        if (q1.getUnit() instanceof TemperatureUnit || q2.getUnit() instanceof TemperatureUnit) {
            // Usually, adding temperatures isn't standard; if you want to block it:
            if (!op.equals("COMPARE") && !op.equals("CONVERT")) {
                 throw new IllegalArgumentException("Temperature does not support " + op + " operation");
            }
        }
    }

    // ================= OPERATIONS =================

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> a = createQuantity(q1);
        Quantity<?> b = createQuantity(q2);
        validateSameType(a, b);
        boolean result = a.equals(b);
        saveToHistory("COMPARE", a, b, String.valueOf(result));
        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        Quantity<?> quantity = createQuantity(input);
        String normalizedTarget = normalizeUnit(targetUnit);
        Object unitType = quantity.getUnit();

        QuantityDTO resultDTO;
        if (unitType instanceof LengthUnit) {
            Quantity<LengthUnit> res = ((Quantity<LengthUnit>) quantity).convertTo(LengthUnit.valueOf(normalizedTarget));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof WeightUnit) {
            Quantity<WeightUnit> res = ((Quantity<WeightUnit>) quantity).convertTo(WeightUnit.valueOf(normalizedTarget));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof VolumeUnit) {
            Quantity<VolumeUnit> res = ((Quantity<VolumeUnit>) quantity).convertTo(VolumeUnit.valueOf(normalizedTarget));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof TemperatureUnit) {
            Quantity<TemperatureUnit> res = ((Quantity<TemperatureUnit>) quantity).convertTo(TemperatureUnit.valueOf(normalizedTarget));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else {
            throw new IllegalArgumentException("Conversion failed");
        }
        
        saveToHistory("CONVERT", quantity, null, resultDTO.getValue() + " " + resultDTO.getUnit());
        return resultDTO;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> a = createQuantity(q1);
        Quantity<?> b = createQuantity(q2);
        validateSameType(a, b);
        validateTemperature(a, b, "ADD");

        Quantity<?> result = ((Quantity) a).add((Quantity) b);
        saveToHistory("ADD", a, b, result.toString());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> a = createQuantity(q1);
        Quantity<?> b = createQuantity(q2);
        validateSameType(a, b);
        validateTemperature(a, b, "SUBTRACT");

        Quantity<?> result = ((Quantity) a).subtract((Quantity) b);
        saveToHistory("SUBTRACT", a, b, result.toString());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> a = createQuantity(q1);
        Quantity<?> b = createQuantity(q2);
        validateSameType(a, b);
        validateTemperature(a, b, "DIVIDE");

        double result = ((Quantity) a).divide((Quantity) b);
        saveToHistory("DIVIDE", a, b, String.valueOf(result));
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        User user = getAuthenticatedUser();
        if (user == null) return new ArrayList<>();
        return repository.findByUserOrderByCreatedAtDesc(user);
    }
}