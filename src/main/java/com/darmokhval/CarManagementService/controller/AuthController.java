package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.LoginDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.TokenPair;
import com.darmokhval.CarManagementService.model.dto.registration.RegistrationDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.service.AuthService;
import com.darmokhval.CarManagementService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("api/auth/register")
    public ResponseEntity<TokenPair> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        TokenPair registeredEntity = authService.register(registrationDTO);
        return ResponseEntity.ok(registeredEntity);
    }

    @PostMapping("api/auth/login")
    public ResponseEntity<TokenPair> loginUser(@RequestBody LoginDTO loginDTO) {
        TokenPair userDTO = authService.loginUser(loginDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("api/auth/refresh")
    public ResponseEntity<TokenPair> refreshTokens(@RequestBody TokenPair tokens) {
        TokenPair tokenPair = authService.refresh(tokens);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("api/auth/manager")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TokenPair> createManager(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.createManager(userDTO));
    }
    @GetMapping("api/auth/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
