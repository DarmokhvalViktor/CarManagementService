package com.darmokhval.CarManagementService.model.dto;

import com.darmokhval.CarManagementService.model.entity.AdvertisementView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class AdvertisementDTO {
    private Long id;
    private String model;
    private String brand;
    private String description;
    private MultipartFile carPhoto;
    private Integer price;
    private String location;
    @Pattern(regexp = "^(USD|EUR|UAH)$", message = "Currency type must be either USD, EUR or UAH")
    private String currencyType;
    private String photoPath;
    private List<AdvertisementView> views = new ArrayList<>();

    public AdvertisementDTO(String model, String brand, MultipartFile carPhoto, Integer price, String currencyType) {
        this.model = model;
        this.brand = brand;
        this.carPhoto = carPhoto;
        this.price = price;
        this.currencyType = currencyType;
    }

    public AdvertisementDTO(String model, String brand, MultipartFile carPhoto) {
        this.model = model;
        this.brand = brand;
        this.carPhoto = carPhoto;
    }
}
