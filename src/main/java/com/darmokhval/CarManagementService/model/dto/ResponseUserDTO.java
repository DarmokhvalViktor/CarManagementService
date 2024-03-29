package com.darmokhval.CarManagementService.model.dto;

import com.darmokhval.CarManagementService.model.entity.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ResponseUserDTO {
    private Long id;
    private String email;
    private String username;
    private Boolean isSeller;
    private Boolean isPremium;
    private String role;
}
