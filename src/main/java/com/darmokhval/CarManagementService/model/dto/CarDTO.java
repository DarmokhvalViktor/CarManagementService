package com.darmokhval.CarManagementService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO {
    private Long id;
    private String model;
    private String brand;
    private MultipartFile carPhoto;
    private String photoPath;

    public CarDTO(String model, String brand, MultipartFile carPhoto) {
        this.model = model;
        this.brand = brand;
        this.carPhoto = carPhoto;
    }
}
