package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.CarNotFoundException;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.CarDTO;
import com.darmokhval.CarManagementService.model.entity.Car;
import com.darmokhval.CarManagementService.model.entity.CarDetails;
import com.darmokhval.CarManagementService.repository.CarDetailsRepository;
import com.darmokhval.CarManagementService.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final MainMapper mainMapper;
    private final CarDetailsRepository carDetailsRepository;
    private String uploadDirectory;

    @Transactional
    public CarDTO saveCar(CarDTO carDTO, MultipartFile multipartFile) {
        savePhoto(multipartFile);
        CarDetails details = new CarDetails();
        details.setBrand(carDTO.getBrand());
        details.setModel(carDTO.getModel());
        details = carDetailsRepository.save(details);

        Car car = new Car();
        car.setCarDetails(details);
        car.setPhotoPath(multipartFile.getOriginalFilename());
        Car savedCar = carRepository.save(car);
        return mainMapper.carEntityToDTO(savedCar);
    }

    public List<CarDTO> getCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(mainMapper::carEntityToDTO).toList();
    }

    public CarDTO getCar(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent()) {
            return car.map(mainMapper::carEntityToDTO).get();
        }
        throw new CarNotFoundException(id);
    }

    @Transactional
    public CarDTO updateCar(Long id, CarDTO carDTO, Optional<MultipartFile> multipartFile) {
        Car existingCar = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        CarDetails details = existingCar.getCarDetails();
        if(!carDTO.getBrand().equals(details.getBrand()) || !carDTO.getModel().equals(details.getModel())) {
            details.setBrand(carDTO.getBrand());
            details.setModel(carDTO.getModel());
            carDetailsRepository.save(details);
        }
        multipartFile.ifPresent(file -> {
            savePhoto(file);
            existingCar.setPhotoPath(file.getOriginalFilename());
        });
        Car updatedCar = carRepository.save(existingCar);
        return mainMapper.carEntityToDTO(updatedCar);
    }


    private void savePhoto(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + fileName;
        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + fileName, e);
        }
    }

    @PostConstruct
    private void createUploadDirectoryIfNotExists() {
        String directoryPath = System.getProperty("user.home") + File.separator + "carPhotos";
        File folder = new File(directoryPath);
        if(!folder.exists()) {
            if(folder.mkdir()) {
                System.out.println("FOlder created successfully");
            } else {
                System.out.println("Failed to create folder");
            }
        } else {
            System.out.println("Folder already exists");
        }
        uploadDirectory = String.valueOf(folder);
    }


    public String deleteCar(Long id) {
        try {
            carRepository.deleteById(id);
        } catch(IllegalArgumentException e) {
            throw new CarNotFoundException(id);
        }
        return String.format("Car with id %d was deleted", id);
    }
}
