package com.qmaservice.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;
}