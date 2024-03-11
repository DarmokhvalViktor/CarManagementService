package com.darmokhval.CarManagementService.model.dto;

import com.darmokhval.CarManagementService.model.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
    private Long id;
    private String email;
    private String username;
    private Boolean isSeller;
    private List<String> roles;
}
