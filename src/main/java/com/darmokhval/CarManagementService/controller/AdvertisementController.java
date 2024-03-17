package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.AveragePriceDTO;
import com.darmokhval.CarManagementService.model.dto.AdvertisementDTO;
import com.darmokhval.CarManagementService.model.dto.CarDetailsDTO;
import com.darmokhval.CarManagementService.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService carService;

    @PostMapping("/api/advertisements")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestPart("advertisement") AdvertisementDTO advertisementDTO,
                                                                @RequestPart("photo") MultipartFile multipartFile) {
        return ResponseEntity.ok(carService.saveAdvertisement(advertisementDTO, multipartFile));
    }
    @GetMapping("/api/advertisements")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisements(@RequestParam(required = false) String location) {
        return ResponseEntity.ok(carService.getAdvertisements(location));
    }
    @GetMapping("/api/advertisements/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getAdvertisement(id));
    }

    @GetMapping("/api/advertisements/details")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CarDetailsDTO>> getCarDetails() {
        List<CarDetailsDTO> newCarDetailsDTO = carService.getAllCarDetails();
        return ResponseEntity.ok(newCarDetailsDTO);
    }
    @GetMapping("/api/advertisements/me")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<AdvertisementDTO>> findMyAdvertisement() {
        List<AdvertisementDTO> cars = carService.getMyAdvertisement();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/api/advertisements/average-price")
    public ResponseEntity<AveragePriceDTO> getAveragePrice(@RequestParam(required = false) String location) {
        return ResponseEntity.ok(carService.getAveragePrice(location));
    }

    @PutMapping("/api/advertisements/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<AdvertisementDTO> updateAdvertisementById(@PathVariable Long id, @RequestPart("car") AdvertisementDTO advertisementDTO,
                                                                    @RequestPart(value = "photo", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(carService.updateAdvertisement(id, advertisementDTO, Optional.ofNullable(multipartFile)));
    }
    @DeleteMapping("/api/advertisements/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAdvertisementById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.deleteAdvertisement(id));
    }

    @PostMapping("/api/advertisements/details")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CarDetailsDTO> createCarDetails(@RequestBody CarDetailsDTO carDetailsDTO) {
        CarDetailsDTO newCarDetailsDTO = carService.createCarDetails(carDetailsDTO);
        return ResponseEntity.ok(newCarDetailsDTO);
    }

    @GetMapping("/api/advertisements/{id}/views")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> getViewsForTimePeriod(@PathVariable Long id, @RequestParam("period") String period) {
        return ResponseEntity.ok(carService.getViewsForTimePeriod(id, period));
    }
}
