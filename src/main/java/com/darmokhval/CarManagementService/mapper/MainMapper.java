package com.darmokhval.CarManagementService.mapper;

import com.darmokhval.CarManagementService.model.dto.CarDTO;
import com.darmokhval.CarManagementService.model.dto.registration.CompanyDTO;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.entity.Car;
import com.darmokhval.CarManagementService.model.entity.CarDetails;
import com.darmokhval.CarManagementService.model.entity.Company;
import com.darmokhval.CarManagementService.model.entity.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;

@Component
public class MainMapper {
    private final String uploadDirectory = System.getProperty("user.home") + File.separator + "carPhotos";

    public User dtoToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setIsSeller(userDTO.getIsSeller());
        user.setIsPremium(userDTO.getIsPremium());
        return user;
    }

    public ResponseUserDTO userEntityToDto(User user) {
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles());
        userDTO.setIsSeller(user.getIsSeller());
        userDTO.setIsPremium(user.getIsPremium());
        return userDTO;
    }

    public Company dtoToCompanyEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setAddress(companyDTO.getAddress());
        company.setEmail(companyDTO.getEmail());
        company.setUsername(companyDTO.getUsername());
        company.setPassword(companyDTO.getPassword());
        company.setIsPremium(companyDTO.getIsPremium());
        return company;
    }

    public CarDTO carEntityToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand(car.getCarDetails().getBrand());
        carDTO.setModel(car.getCarDetails().getModel());
        carDTO.setId(car.getId());
        carDTO.setPhotoPath(uploadDirectory + File.separator + car.getPhotoPath());
        return carDTO;
    }
}
