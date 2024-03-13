package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.CarDTO;
import com.darmokhval.CarManagementService.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;


//    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/cars")
    public ResponseEntity<?> createCar(@RequestPart("car") CarDTO carDTO,
                                       @RequestPart("photo") MultipartFile multipartFile) {
        return new ResponseEntity<>(carService.saveCar(carDTO, multipartFile), HttpStatus.CREATED);
    }
    @GetMapping("/cars")
    public ResponseEntity<?> getCars() {
        return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
    }
    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.getCar(id), HttpStatus.OK);
    }
    @PutMapping("/cars/{id}")
    public ResponseEntity<?> updateCarById(@PathVariable Long id, @RequestPart("car") CarDTO carDTO,
                                           @RequestPart(value = "photo", required = false) MultipartFile multipartFile) {
        return new ResponseEntity<>(carService.updateCar(id, carDTO, Optional.ofNullable(multipartFile)), HttpStatus.OK);
    }
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.deleteCar(id), HttpStatus.OK);
    }
}
