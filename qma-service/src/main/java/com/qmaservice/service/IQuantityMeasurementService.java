package com.qmaservice.service;

import java.util.List;

import com.qmaservice.dto.*;
import com.qmaservice.model.*;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO input, String targetUnit);

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);

    List<QuantityMeasurementEntity> getAllMeasurements();
}