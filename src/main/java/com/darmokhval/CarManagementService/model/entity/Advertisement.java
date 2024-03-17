package com.darmokhval.CarManagementService.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinTable(name = "advertisement_car_details",
        joinColumns = @JoinColumn(name = "advertisement_id"),
        inverseJoinColumns = @JoinColumn(name = "car_details_id"))
    private CarDetails carDetails;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @ManyToOne()
    private Company company;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "location")
    private String location;

    private Integer price;
    @Pattern(regexp = "^(USD|EUR|UAH)$", message = "Currency type must be either USD, EUR or UAH")
    private String currencyType;

    @OneToMany(mappedBy = "advertisement")
    private List<AdvertisementView> views = new ArrayList<>();


}
