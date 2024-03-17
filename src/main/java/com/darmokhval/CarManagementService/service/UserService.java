package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.CannotAccessToUserException;
import com.darmokhval.CarManagementService.exception.EntityNotFoundException;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.model.entity.ERole;
import com.darmokhval.CarManagementService.model.entity.User;
import com.darmokhval.CarManagementService.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MainMapper mainMapper;

    public List<ResponseUserDTO> getUsers() {
        return userRepository.findAll().stream().map(mainMapper::userEntityToDto).toList();
    }

    public ResponseUserDTO getUser(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        User userFromContext = extractUserFromContext();
        if(userFromContext.getId().equals(user.get().getId())) {
            return mainMapper.userEntityToDto(user.get());
        } else {
            throw new CannotAccessToUserException(user.get().getId());
        }
    }

    @Transactional
    public ResponseUserDTO updateUser(UserDTO userDTO, Long id) {
        Optional<User> user = userRepository.findById(id);
        User userFromContext = extractUserFromContext();
        if(user.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        if(userFromContext.getId().equals(user.get().getId())) {
            user.get().setEmail(userDTO.getEmail());
            user.get().setUsername(userDTO.getUsername());
            user.get().setIsSeller(userDTO.getIsSeller());
            user.get().setPassword(userDTO.getPassword());
            if(userDTO.getIsSeller() != null) {
                user.get().setRole(ERole.ROLE_SELLER.getRole());
            }
            return mainMapper.userEntityToDto(userRepository.save(user.get()));
        } else {
            throw new CannotAccessToUserException(user.get().getId());
        }
    }

    @Transactional
    public String deleteUser(Long id) {
        Optional<User> deletedUser = userRepository.findById(id);
        if(deletedUser.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        userRepository.deleteById(id);
        return String.format("User with id %s successfully deleted", id);
    }

    @Transactional
    public AuthResponse buyPremium(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        User userFromContext = extractUserFromContext();
        if(userFromContext.getId().equals(user.get().getId())) {
            user.get().setIsPremium(true);
            userRepository.save(user.get());
            return AuthResponse.builder()
                    .id(user.get().getId())
                    .username(user.get().getUsername())
                    .message("User now has premium account")
                    .role(user.get().getRole())
                    .build();
        } else {
            throw new CannotAccessToUserException(user.get().getId());
        }
    }

    private User extractUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
    }
}
