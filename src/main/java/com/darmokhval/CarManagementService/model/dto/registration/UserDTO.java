package com.darmokhval.CarManagementService.model.dto.registration;

import com.darmokhval.CarManagementService.model.entity.ERole;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends RegistrationDTO {
    private String username;
    private String email;
    private String password;
    private Boolean isSeller;
    private String type;

    @Override
    public String getType() {
        return "user";
    }
}
