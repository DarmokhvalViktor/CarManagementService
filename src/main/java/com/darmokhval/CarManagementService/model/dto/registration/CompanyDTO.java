package com.darmokhval.CarManagementService.model.dto.registration;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO extends RegistrationDTO{
    private String username;
    private String address;
    private String password;
    private String email;
    private String type;
    private Boolean isSeller;

    @Override
    public String getType() {
        return "company";
    }
}
