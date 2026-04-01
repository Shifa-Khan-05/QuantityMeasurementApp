package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public boolean compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    @PostMapping("/convert/{targetUnit}")
    public QuantityDTO convert(@RequestBody QuantityDTO input, @PathVariable String targetUnit) {
        return service.convert(input, targetUnit);
    }

    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityInputDTO input) {
        return service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody QuantityInputDTO input) {
        return service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    @PostMapping("/divide")
    public double divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO());
    }

    @GetMapping("/history")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistory() {
        List<QuantityMeasurementEntity> history = service.getAllMeasurements();
        return ResponseEntity.ok(history);
    }
}