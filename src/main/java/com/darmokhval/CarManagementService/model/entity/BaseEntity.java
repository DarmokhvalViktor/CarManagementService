package com.darmokhval.CarManagementService.model.entity;

import java.util.Set;

public interface BaseEntity {
    String getPassword();
    String getUsername();
    Set<String> getRoles();
    String getEmail();
}
