////package com.qmaservice.service;
////
////import java.util.List;
////import java.util.ArrayList;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.stereotype.Service;
////import com.qmaservice.model.*;
////import com.qmaservice.repository.*;
////import com.qmaservice.dto.*;
////
////@Service
////public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
////
////    @Autowired
////    private QuantityMeasurementRepository repository;
////
////    private String getAuthenticatedUserEmail() {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
////            return null;
////        }
////        return auth.getName(); 
////    }
////
////    @Override
////    public List<QuantityMeasurementEntity> getAllMeasurements() {
////        String email = getAuthenticatedUserEmail();
////        if (email == null) return new ArrayList<>();
////        // Fixed: Query by Email string
////        return repository.findByUserEmailOrderByCreatedAtDesc(email);
////    }
////
////    @Override
////    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
////        // ... calculation logic ...
////        String email = getAuthenticatedUserEmail();
////        if (email != null) {
////            repository.save(new QuantityMeasurementEntity(email, "ADD", q1.toString(), q2.toString(), "result"));
////        }
////        return new QuantityDTO(0.0, "METRE"); // Replace with actual result
////    }
////
////    // Include other methods (convert, subtract, etc.) similarly...
////    @Override public boolean compare(QuantityDTO q1, QuantityDTO q2) { return false; }
////    @Override public QuantityDTO convert(QuantityDTO input, String targetUnit) { return null; }
////    @Override public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) { return null; }
////    @Override public double divide(QuantityDTO q1, QuantityDTO q2) { return 0; }
////}
//
//
//
//
//
//
//package com.qmaservice.service;
//
//import java.util.List;
//import java.util.ArrayList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.qmaservice.core.*;
//import com.qmaservice.model.*;
//import com.qmaservice.repository.*;
//import com.qmaservice.dto.*;
//
//@Service
//public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
//
//    @Autowired
//    private QuantityMeasurementRepository repository;
//
//    private String getAuthenticatedUserEmail() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
//            return null;
//        }
//        return auth.getName(); 
//    }
//
//    /**
//     * Helper to convert DTO to actual Logic Objects from your Core package
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private Quantity<?> createQuantity(QuantityDTO dto) {
//        double value = dto.getValue();
//        String unitStr = dto.getUnit().trim().toUpperCase();
//
//        
//        if (!unitStr.endsWith("S") && !unitStr.equals("FEET")) {
//            unitStr = unitStr + "S";
//        }
//        
//        // Spelling correction for American vs British if necessary
//        if (unitStr.equals("METRES")) unitStr = "METERS";
//        if (unitStr.equals("CENTIMETRES")) unitStr = "CENTIMETERS";
//
//        System.out.println("DEBUG: Mapping " + dto.getUnit() + " to Enum: " + unitStr);
//
//        try { return new Quantity<>(value, LengthUnit.valueOf(unitStr)); } catch (Exception e) {}
//        try { return new Quantity<>(value, WeightUnit.valueOf(unitStr)); } catch (Exception e) {}
//        try { return new Quantity<>(value, VolumeUnit.valueOf(unitStr)); } catch (Exception e) {}
//        try { return new Quantity<>(value, TemperatureUnit.valueOf(unitStr)); } catch (Exception e) {}
//        
//        throw new IllegalArgumentException("Unsupported Unit: " + unitStr);
//    }
//
//    @Override
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
//        Quantity qty1 = createQuantity(q1);
//        Quantity qty2 = createQuantity(q2);
//        
//        // 1. Perform actual calculation
//        Quantity result = qty1.add(qty2);
//        
//        // 2. Format result for returning to UI
//        QuantityDTO resultDto = new QuantityDTO(result.getValue(), result.getUnit().toString());
//
//        // 3. Save clean strings to History
//        saveHistory("ADD", q1, q2, resultDto);
//        
//        return resultDto;
//    }
//
//    @Override
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
//        Quantity qty1 = createQuantity(q1);
//        Quantity qty2 = createQuantity(q2);
//        
//        Quantity result = qty1.subtract(qty2);
//        QuantityDTO resultDto = new QuantityDTO(result.getValue(), result.getUnit().toString());
//
//        saveHistory("SUBTRACT", q1, q2, resultDto);
//        
//        return resultDto;
//    }
//
//    @Override
//    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
//        // Implementation depends on your Core logic conversion method
//        // But following your pattern:
//        Quantity<?> qty = createQuantity(input);
//        return new QuantityDTO(qty.getValue(), targetUnit); 
//    }
//
//    @Override
//    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
//        return createQuantity(q1).equals(createQuantity(q2));
//    }
//
//    @Override
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public double divide(QuantityDTO q1, QuantityDTO q2) {
//        Quantity qty1 = createQuantity(q1);
//        Quantity qty2 = createQuantity(q2);
//        return qty1.divide(qty2);
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> getAllMeasurements() {
//        String email = getAuthenticatedUserEmail();
//        return (email == null) ? new ArrayList<>() : repository.findByUserEmailOrderByCreatedAtDesc(email);
//    }
//
//    /**
//     * UNIQUE FIX: Formats strings so DB stores "1.0 METRE" instead of DTO object strings
//     */
//    private void saveHistory(String op, QuantityDTO q1, QuantityDTO q2, QuantityDTO res) {
//        String email = getAuthenticatedUserEmail();
//        if (email != null) {
//            String op1Str = q1.getValue() + " " + q1.getUnit();
//            String op2Str = q2.getValue() + " " + q2.getUnit();
//            String resStr = res.getValue() + " " + res.getUnit();
//            
//            repository.save(new QuantityMeasurementEntity(email, op, op1Str, op2Str, resStr));
//        }
//    }
//}


package com.qmaservice.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.qmaservice.core.*;
import com.qmaservice.model.*;
import com.qmaservice.repository.*;
import com.qmaservice.dto.*;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    private String getAuthenticatedUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        return auth.getName(); 
    }

    private Quantity<?> createQuantity(QuantityDTO dto) {
        double value = dto.getValue();
        String unitStr = dto.getUnit().trim().toUpperCase();

        // Standardizing pluralization for your Enums
        if (!unitStr.endsWith("S") && !unitStr.equals("FEET")) {
            unitStr = unitStr + "S";
        }
        if (unitStr.equals("METRES")) unitStr = "METERS";
        if (unitStr.equals("CENTIMETRES")) unitStr = "CENTIMETERS";

        try { return new Quantity<>(value, LengthUnit.valueOf(unitStr)); } catch (Exception e) {}
        try { return new Quantity<>(value, WeightUnit.valueOf(unitStr)); } catch (Exception e) {}
        try { return new Quantity<>(value, VolumeUnit.valueOf(unitStr)); } catch (Exception e) {}
        try { return new Quantity<>(value, TemperatureUnit.valueOf(unitStr)); } catch (Exception e) {}
        
        throw new IllegalArgumentException("Unsupported Unit: " + unitStr);
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        Quantity qty1 = createQuantity(q1);
        Quantity qty2 = createQuantity(q2);
        Quantity result = qty1.add(qty2);
        
        saveHistory("ADD", q1, q2, result.getValue() + " " + result.getUnit());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        Quantity qty1 = createQuantity(q1);
        Quantity qty2 = createQuantity(q2);
        Quantity result = qty1.subtract(qty2);
        
        saveHistory("SUBTRACT", q1, q2, result.getValue() + " " + result.getUnit());
        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        Quantity qty1 = createQuantity(q1);
        Quantity qty2 = createQuantity(q2);
        double result = qty1.divide(qty2);
        
        // Save Divide history
        saveHistory("DIVIDE", q1, q2, String.valueOf(result));
        return result;
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        boolean result = createQuantity(q1).equals(createQuantity(q2));
        
        // Save Compare history
        saveHistory("COMPARE", q1, q2, result ? "EQUAL" : "NOT EQUAL");
        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        Quantity<?> qty = createQuantity(input);
        // If your core has a specific convert method, call it here. 
        // Example assumes target value is needed:
        saveHistory("CONVERT", input, new QuantityDTO(0.0, targetUnit), "Converted to " + targetUnit);
        return new QuantityDTO(qty.getValue(), targetUnit); 
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        String email = getAuthenticatedUserEmail();
        return (email == null) ? new ArrayList<>() : repository.findByUserEmailOrderByCreatedAtDesc(email);
    }

    private void saveHistory(String op, QuantityDTO q1, QuantityDTO q2, String resultStr) {
        String email = getAuthenticatedUserEmail();
        if (email != null) {
            String op1Str = q1.getValue() + " " + q1.getUnit();
            String op2Str = q2.getValue() + " " + q2.getUnit();
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                email, op, op1Str, op2Str, resultStr
            );
            repository.save(entity);
        }
    }
}