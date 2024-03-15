package com.darmokhval.CarManagementService.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinTable(name = "car_car_details",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "car_details_id"))
    private CarDetails carDetails;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Company company;

    @Column(name = "photo_path")
    private String photoPath;

    private Integer price;
    @Pattern(regexp = "^(USD|EUR|UAH)$", message = "Currency type must be either USD, EUR or UAH")
    private String currencyType;
}
