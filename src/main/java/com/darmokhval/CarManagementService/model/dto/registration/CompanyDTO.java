package com.darmokhval.CarManagementService.model.dto.registration;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class CompanyDTO extends RegistrationDTO{
    private String username;
    private String address;
    private String password;
    private String email;
    private String type;
    private Boolean isSeller;
    private Boolean isPremium;
    private Integer numberOfAds;

    @Override
    public String getType() {
        return "company";
    }
}
