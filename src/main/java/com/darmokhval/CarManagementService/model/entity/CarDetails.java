package com.darmokhval.CarManagementService.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;

    @OneToMany(mappedBy = "carDetails")
    private List<Car> cars;

    public CarDetails(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
}
