package com.darmokhval.CarManagementService.model.dto.registration;

import com.darmokhval.CarManagementService.model.entity.ERole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String message;
    private String username;
    private Long id;
    private List<String> roles;
}
