package com.darmokhval.CarManagementService.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "field username should not be empty")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "field password should not be empty")
    private String password;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Field email should not be empty")
    private String email;

    @Column(name = "seller")
    private Boolean isSeller;

    @Column(name = "premium_account")
    private Boolean isPremium;

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<String> roles = new ArrayList<>();



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public void addRole(String role) {
        this.roles.add(role);
    }
}
