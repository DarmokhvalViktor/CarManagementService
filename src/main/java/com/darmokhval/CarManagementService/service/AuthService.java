package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.PasswordMismatchException;
import com.darmokhval.CarManagementService.exception.EntityNotFoundException;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.LoginDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.registration.CompanyDTO;
import com.darmokhval.CarManagementService.model.dto.registration.RegistrationDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.entity.Company;
import com.darmokhval.CarManagementService.model.entity.ERole;
import com.darmokhval.CarManagementService.model.entity.User;
import com.darmokhval.CarManagementService.repository.CompanyRepository;
import com.darmokhval.CarManagementService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final MainMapper mainMapper;


    public AuthResponse register(RegistrationDTO registrationDTO) {
        String type = registrationDTO.getType();
        if(!RegistrationDTO.isValidType(type)) {
            throw new IllegalArgumentException("Unsupported registration type");
        }

        if(registrationDTO.getType().equalsIgnoreCase("company")) {
            Company company = mainMapper.dtoToCompanyEntity((CompanyDTO) registrationDTO);
            company.addRole(ERole.ROLE_SELLER.getRole());
            companyRepository.save(company);

            return AuthResponse.builder()
                    .id(company.getId())
                    .username(registrationDTO.getUsername())
                    .message("Company was created")
                    .roles(company.getRoles())
                    .build();
        } else {
            User user = mainMapper.dtoToUserEntity((UserDTO) registrationDTO);
            user.addRole(ERole.ROLE_BUYER.getRole());
            if(registrationDTO.getIsSeller()) {
                user.addRole(ERole.ROLE_SELLER.getRole());
            }
            userRepository.save(user);
            return AuthResponse.builder()
                    .id(user.getId())
                    .username(registrationDTO.getUsername())
                    .message("User was created")
                    .roles(user.getRoles())
                    .build();
        }
    }

    public AuthResponse loginUser(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
        Optional<Company> company = companyRepository.findByEmail(loginDTO.getEmail());
        if(user.isEmpty() && company.isEmpty()) {
            throw new EntityNotFoundException(loginDTO.getEmail());
        }
        if(user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword())) {
            return AuthResponse.builder()
                    .id(user.get().getId())
                    .username(user.get().getUsername())
                    .roles(user.get().getRoles())
                    .message("User logged in")
                    .build();
        }
        if(company.isPresent() && company.get().getPassword().equals(loginDTO.getPassword())) {
            return AuthResponse.builder()
                    .id(company.get().getId())
                    .username(company.get().getUsername())
                    .roles(company.get().getRoles())
                    .message("Company logged in")
                    .build();
        }
        throw new PasswordMismatchException();
    }
}
