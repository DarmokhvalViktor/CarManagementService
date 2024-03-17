package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.registration.AuthResponse;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("api/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ResponseUserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<ResponseUserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("api/users/{id}")
    public ResponseEntity<ResponseUserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(userDTO, id));
    }

    @DeleteMapping("api/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String>deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("api/users/{id}/premium")
    public ResponseEntity<AuthResponse> buyPremium(@PathVariable Long id) {
        return ResponseEntity.ok(userService.buyPremium(id));
    }
}
