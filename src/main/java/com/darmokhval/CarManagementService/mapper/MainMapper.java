package com.darmokhval.CarManagementService.mapper;

import com.darmokhval.CarManagementService.model.dto.AdvertisementDTO;
import com.darmokhval.CarManagementService.model.dto.MessageDTO;
import com.darmokhval.CarManagementService.model.dto.registration.CompanyDTO;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.entity.*;
import org.springframework.stereotype.Component;

import java.io.File;

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
        userDTO.setRole(user.getRole());
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

    public AdvertisementDTO advertisementEntityToDTO(Advertisement advertisement) {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();
        advertisementDTO.setBrand(advertisement.getCarDetails().getBrand());
        advertisementDTO.setModel(advertisement.getCarDetails().getModel());
        advertisementDTO.setDescription(advertisement.getDescription());
        advertisementDTO.setPrice(advertisement.getPrice());
        advertisementDTO.setLocation(advertisement.getLocation());
        advertisementDTO.setCurrencyType(advertisement.getCurrencyType());
        advertisementDTO.setId(advertisement.getId());
        advertisementDTO.setViews(advertisement.getViews());
        advertisementDTO.setPhotoPath(uploadDirectory + File.separator + advertisement.getPhotoPath());
        return advertisementDTO;
    }

    public MessageDTO messageEntityToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        dto.setSenderId(message.getSenderId());
        dto.setRecipientId(message.getRecipientId());
        return dto;
    }
}
