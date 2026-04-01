package com.app.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.User;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    // Find history for a specific user, newest first
    List<QuantityMeasurementEntity> findByUserOrderByCreatedAtDesc(User user);
}