package com.darmokhval.CarManagementService.model.entity;

import jakarta.persistence.*;
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
public class Company implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String address;
    private String password;
    private String email;
    private Boolean isPremium;
    private Integer numberOfAds;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "company")
    private List<Advertisement> advertisements = new ArrayList<>();

    public Company(String username, String address, String password, String email) {
        this.username = username;
        this.address = address;
        this.password = password;
        this.email = email;
    }
}
