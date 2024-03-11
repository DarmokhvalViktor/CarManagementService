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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String address;
    private String password;
    private String email;
    private String isPremium;

    @ElementCollection
    @CollectionTable(name = "company_roles", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<String> roles = new ArrayList<>();

    public Company(String username, String address, String password, String email) {
        this.username = username;
        this.address = address;
        this.password = password;
        this.email = email;
    }
    public void addRole(String role) {
        this.roles.add(role);
    }
}
