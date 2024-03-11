package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.ResponseUserDTO;
import com.darmokhval.CarManagementService.model.dto.registration.UserDTO;
import com.darmokhval.CarManagementService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("api/users")
    public ResponseEntity<?> getUsers() {
        List<ResponseUserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        ResponseUserDTO user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("api/users/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
        ResponseUserDTO user = userService.updateUser(userDTO, id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("api/users/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
