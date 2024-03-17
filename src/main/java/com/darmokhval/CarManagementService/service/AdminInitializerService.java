package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.model.entity.ERole;
import com.darmokhval.CarManagementService.model.entity.User;
import com.darmokhval.CarManagementService.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminInitializerService {
    private final UserRepository userRepository;

    @PostConstruct
    public void postInit() {
        Optional<User> admin1 = userRepository.findByUsername("admin1");
        Optional<User> admin2 = userRepository.findByUsername("admin2");
        Optional<User> admin3 = userRepository.findByUsername("admin3");
        Optional<User> admin4 = userRepository.findByUsername("admin4");
        Optional<User> admin5 = userRepository.findByUsername("admin5");
        System.out.println(admin1);
        System.out.println(admin2);
        System.out.println(admin3);
        System.out.println(admin4);
        System.out.println(admin5);
        List<Optional<User>> users = Arrays.asList(admin1, admin2, admin3, admin4, admin5);
        for (int i = 1; i <= 5; i++) {
            if(users.get(i - 1).isEmpty()) {
                User newUser = new User();
                newUser.setUsername("admin" + i);
                newUser.setEmail("admin" + i + "@gmail.com");
                newUser.setRole(ERole.ROLE_ADMIN.getRole());
                newUser.setPassword("admin12345"+ i);
                userRepository.save(newUser);
            }
        }
    }
}
