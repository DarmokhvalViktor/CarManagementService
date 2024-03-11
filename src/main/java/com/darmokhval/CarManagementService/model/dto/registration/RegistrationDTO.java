package com.darmokhval.CarManagementService.model.dto.registration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserDTO.class, name = "user"),
        @JsonSubTypes.Type(value = CompanyDTO.class, name = "company")
})
public abstract class RegistrationDTO {
    String username;
    String email;
    String password;
    String type;
    Boolean isSeller;

    public RegistrationDTO() {
    }

    public abstract String getUsername();
    public abstract String getEmail();
    public abstract String getPassword();
    public abstract String getType();
    public abstract Boolean getIsSeller();

    public static Boolean isValidType(String type) {
        return "user".equalsIgnoreCase(type) || "company".equalsIgnoreCase(type);
    }
}
