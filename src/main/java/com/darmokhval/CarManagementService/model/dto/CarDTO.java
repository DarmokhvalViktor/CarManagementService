package com.darmokhval.CarManagementService.model.dto;

import jakarta.validation.constraints.Pattern;
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
    private String description;
    private MultipartFile carPhoto;
    private Integer price;
    @Pattern(regexp = "^(USD|EUR|UAH)$", message = "Currency type must be either USD, EUR or UAH")
    private String currencyType;
    private String photoPath;

    public CarDTO(String model, String brand, MultipartFile carPhoto, Integer price, String currencyType) {
        this.model = model;
        this.brand = brand;
        this.carPhoto = carPhoto;
        this.price = price;
        this.currencyType = currencyType;
    }

    public CarDTO(String model, String brand, MultipartFile carPhoto) {
        this.model = model;
        this.brand = brand;
        this.carPhoto = carPhoto;
    }
}
