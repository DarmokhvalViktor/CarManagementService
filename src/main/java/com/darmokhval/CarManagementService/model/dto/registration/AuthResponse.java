package com.darmokhval.CarManagementService.model.dto.registration;

import com.darmokhval.CarManagementService.model.entity.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Schema
public class AuthResponse {
    private String message;
    private String username;
    private Long id;
    private String role;
}
