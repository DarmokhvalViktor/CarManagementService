package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.config.jwt.JwtUtils;
import com.darmokhval.CarManagementService.config.jwt.MyCustomUserDetails;
import com.darmokhval.CarManagementService.exception.InvalidRefreshTokenSignatureException;
import com.darmokhval.CarManagementService.exception.PasswordMismatchException;
import com.darmokhval.CarManagementService.exception.EntityNotFoundException;
import com.darmokhval.CarManagementService.exception.RefreshTokenHasExpiredException;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.LoginDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.TokenPair;
import com.darmokhval.CarManagementService.model.dto.registration.CompanyDTO;
import com.darmokhval.CarManagementService.model.dto.registration.RegistrationDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.entity.BaseEntity;
import com.darmokhval.CarManagementService.model.entity.Company;
import com.darmokhval.CarManagementService.model.entity.ERole;
import com.darmokhval.CarManagementService.model.entity.User;
import com.darmokhval.CarManagementService.repository.CompanyRepository;
import com.darmokhval.CarManagementService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MainMapper mainMapper;


    public TokenPair register(RegistrationDTO registrationDTO) {
        String type = registrationDTO.getType();
        TokenPair tokens = new TokenPair();
        if(!RegistrationDTO.isValidType(type)) {
            throw new IllegalArgumentException("Unsupported registration type");
        }

        if(registrationDTO.getType().equalsIgnoreCase("company")) {
            Company company = mainMapper.dtoToCompanyEntity((CompanyDTO) registrationDTO);
            company.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            company.setRole(ERole.ROLE_SELLER.getRole());
            company = companyRepository.save(company);

            tokens = createTokenPair(company);
        } else {
            User user = mainMapper.dtoToUserEntity((UserDTO) registrationDTO);

            user.setRole(registrationDTO.getIsSeller() ? ERole.ROLE_SELLER.getRole() : ERole.ROLE_BUYER.getRole());
            user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            user = userRepository.save(user);
            tokens = createTokenPair(user);
        }
        return tokens;
    }

    public TokenPair loginUser(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
        Optional<Company> company = companyRepository.findByEmail(loginDTO.getEmail());
        if(user.isEmpty() && company.isEmpty()) {
            throw new EntityNotFoundException(loginDTO.getEmail());
        }
        if(user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword())) {
            return createTokenPair(user.get());
        }
        if(company.isPresent() && company.get().getPassword().equals(loginDTO.getPassword())) {
            return createTokenPair(company.get());
        }
        throw new PasswordMismatchException();
    }

    public TokenPair createManager(UserDTO userDTO) {
        User user = mainMapper.dtoToUserEntity(userDTO);
        user.setRole(ERole.ROLE_MANAGER.getRole());
        user = userRepository.save(user);
        return createTokenPair(user);
    }

    public TokenPair refresh(TokenPair tokens) {
        String refreshToken = tokens.getRefreshToken();
        Date refreshTokenExpiration = jwtUtils.extractExpiration(refreshToken);
        if(refreshTokenExpiration.before(new Date())) {
            throw new RefreshTokenHasExpiredException();
        }
        if(!jwtUtils.isRefreshToken(refreshToken) || !jwtUtils.isMyCustomToken(refreshToken)) {
            throw new InvalidRefreshTokenSignatureException();
        }
        String username = jwtUtils.getUsernameFromJwt(refreshToken);
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new EntityNotFoundException(username);
        }
        return createTokenPair(user.get());
    }

    private TokenPair createTokenPair(BaseEntity entity) {
        MyCustomUserDetails userDetails = new MyCustomUserDetails(
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority(entity.getRole())));
        String accessToken = jwtUtils.generateAccessToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        return new TokenPair(accessToken, refreshToken);

    }
}
