package com.darmokhval.CarManagementService.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class CarDetailsDTO {
    private String brand;
    private String model;
}
