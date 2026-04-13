package com.qmaservice.repository;

import com.qmaservice.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    // Standard JPA naming convention
    List<QuantityMeasurementEntity> findByUserEmailOrderByCreatedAtDesc(String userEmail);
}