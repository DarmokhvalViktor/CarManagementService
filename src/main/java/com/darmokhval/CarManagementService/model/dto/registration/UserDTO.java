package com.darmokhval.CarManagementService.model.dto.registration;

import com.darmokhval.CarManagementService.model.entity.ERole;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema
public class UserDTO extends RegistrationDTO {
    private String username;
    private String email;
    private String password;
    private Boolean isSeller;
    private Boolean isPremium;
    private String type;
    private Integer numberOfAds;

    @Override
    public String getType() {
        return "user";
    }
}
