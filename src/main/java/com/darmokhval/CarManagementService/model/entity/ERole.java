package com.darmokhval.CarManagementService.model.entity;

public enum ERole {
    ROLE_BUYER("ROLE_BUYER"),
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    ERole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
