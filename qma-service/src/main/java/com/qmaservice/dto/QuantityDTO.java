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
	
	@NotNull
    private double value;
	
	@NotEmpty
    private String unit;

}