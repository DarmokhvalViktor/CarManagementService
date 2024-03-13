package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.LoginDTO;
import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.registration.RegistrationDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("api/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        AuthResponse registeredEntity = authService.register(registrationDTO);
        return ResponseEntity.ok(registeredEntity);
    }

    @PostMapping("api/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        AuthResponse userDTO = authService.loginUser(loginDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("api/auth/manager")
    public ResponseEntity<?> createManager(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.createManager(userDTO));
    }
}
