package com.darmokhval.CarManagementService.mapper;

import com.darmokhval.CarManagementService.model.dto.registration.CompanyDTO;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.entity.Company;
import com.darmokhval.CarManagementService.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MainMapper {

    public User dtoToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setIsSeller(userDTO.getIsSeller());
        return user;
    }

    public ResponseUserDTO userEntityToDto(User user) {
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles());
        userDTO.setIsSeller(user.getIsSeller());
        return userDTO;
    }

    public Company dtoToCompanyEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setAddress(companyDTO.getAddress());
        company.setEmail(companyDTO.getEmail());
        company.setUsername(companyDTO.getUsername());
        company.setPassword(companyDTO.getPassword());
        return company;
    }
}
