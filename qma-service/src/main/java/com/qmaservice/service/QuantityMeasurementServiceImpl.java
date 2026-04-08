package com.qmaservice.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.qmaservice.core.*;
import com.qmaservice.dto.*;
import com.qmaservice.model.*;
import com.qmaservice.repository.*;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    @Autowired
    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    /**
     * Helper: Get Authenticated User ID from Security Context.
     * In Microservices, we extract the ID from the JWT claims 
     * rather than querying a local User database.
     */
    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        // Assuming your JwtAuthenticationFilter sets the User ID as the principal or in details
        try {
            return Long.parseLong(auth.getName()); 
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void saveToHistory(Long userId, String op, String op1, String op2, String res) {
        if (userId != null) {
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(userId, op, op1, op2, res);
            repository.save(entity);
        }
    }

    private Quantity<?> createQuantity(QuantityDTO dto) {
        try { return new Quantity<>(dto.getValue(), LengthUnit.valueOf(dto.getUnit())); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), WeightUnit.valueOf(dto.getUnit())); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), VolumeUnit.valueOf(dto.getUnit())); } catch (Exception ignored) {}
        try { return new Quantity<>(dto.getValue(), TemperatureUnit.valueOf(dto.getUnit())); } catch (Exception ignored) {}
        throw new IllegalArgumentException("Unsupported Unit: " + dto.getUnit());
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        return createQuantity(q1).equals(createQuantity(q2));
    }

    @Override
    @Cacheable(value = "calculations", key = "{#input.value, #input.unit, #targetUnit}")
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        Quantity<?> quantity = createQuantity(input);
        Object unitType = quantity.getUnit();

        QuantityDTO resultDTO;
        if (unitType instanceof LengthUnit) {
            Quantity<LengthUnit> res = ((Quantity<LengthUnit>) quantity).convertTo(LengthUnit.valueOf(targetUnit));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof WeightUnit) {
            Quantity<WeightUnit> res = ((Quantity<WeightUnit>) quantity).convertTo(WeightUnit.valueOf(targetUnit));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof VolumeUnit) {
            Quantity<VolumeUnit> res = ((Quantity<VolumeUnit>) quantity).convertTo(VolumeUnit.valueOf(targetUnit));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else if (unitType instanceof TemperatureUnit) {
            Quantity<TemperatureUnit> res = ((Quantity<TemperatureUnit>) quantity).convertTo(TemperatureUnit.valueOf(targetUnit));
            resultDTO = new QuantityDTO(res.getValue(), res.getUnit().name());
        } else {
            throw new IllegalArgumentException("Conversion Logic failed for Unit Type");
        }
        return resultDTO;
    }

    @Override
    @CacheEvict(value = "history", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> quantity1 = createQuantity(q1);
        Quantity<?> quantity2 = createQuantity(q2);
        Quantity<?> result = ((Quantity) quantity1).add((Quantity) quantity2);

        Long userId = getAuthenticatedUserId();
        saveToHistory(userId, "ADD", quantity1.toString(), quantity2.toString(), result.toString());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    @CacheEvict(value = "history", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> quantity1 = createQuantity(q1);
        Quantity<?> quantity2 = createQuantity(q2);
        Quantity<?> result = ((Quantity) quantity1).subtract((Quantity) quantity2);

        Long userId = getAuthenticatedUserId();
        saveToHistory(userId, "SUBTRACT", quantity1.toString(), quantity2.toString(), result.toString());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    @CacheEvict(value = "history", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        Quantity<?> quantity1 = createQuantity(q1);
        Quantity<?> quantity2 = createQuantity(q2);
        double result = ((Quantity) quantity1).divide((Quantity) quantity2);

        Long userId = getAuthenticatedUserId();
        saveToHistory(userId, "DIVIDE", quantity1.toString(), quantity2.toString(), String.valueOf(result));
        return result;
    }

    @Override
    @Cacheable(value = "history", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        Long userId = getAuthenticatedUserId();
        if (userId == null) return new ArrayList<>();
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}