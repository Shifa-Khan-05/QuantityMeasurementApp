package com.qmaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.qmaservice.model.*;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    // Find history for a specific user, newest first
    List<QuantityMeasurementEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}