package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.CarDTO;
import com.darmokhval.CarManagementService.model.dto.CarDetailsDTO;
import com.darmokhval.CarManagementService.model.entity.CarDetails;
import com.darmokhval.CarManagementService.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/cars")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCarById(@PathVariable Long id, @RequestPart("car") CarDTO carDTO,
                                           @RequestPart(value = "photo", required = false) MultipartFile multipartFile) {
        return new ResponseEntity<>(carService.updateCar(id, carDTO, Optional.ofNullable(multipartFile)), HttpStatus.OK);
    }
    @DeleteMapping("/cars/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.deleteCar(id), HttpStatus.OK);
    }

    @PostMapping("/cars/details")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCarDetails(@RequestBody CarDetailsDTO carDetailsDTO) {
        CarDetailsDTO newCarDetailsDTO = carService.createCarDetails(carDetailsDTO);
        return new ResponseEntity<>(newCarDetailsDTO, HttpStatus.OK);
    }
    @GetMapping("/cars/details")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCarDetails() {
        List<CarDetailsDTO> newCarDetailsDTO = carService.getAllCarDetails();
        return new ResponseEntity<>(newCarDetailsDTO, HttpStatus.OK);
    }
}
