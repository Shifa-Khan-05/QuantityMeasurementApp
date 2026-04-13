package com.qmaservice.dto;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "Value cannot be null")
    private double value;
    
    @NotEmpty(message = "Unit cannot be empty")
    private String unit;
}