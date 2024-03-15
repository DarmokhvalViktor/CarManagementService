package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.*;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.CarDTO;
import com.darmokhval.CarManagementService.model.dto.CarDetailsDTO;
import com.darmokhval.CarManagementService.model.entity.*;
import com.darmokhval.CarManagementService.repository.CarDetailsRepository;
import com.darmokhval.CarManagementService.repository.CarRepository;
import com.darmokhval.CarManagementService.repository.HardLanguageRepository;
import com.darmokhval.CarManagementService.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final MainMapper mainMapper;
    private final CarDetailsRepository carDetailsRepository;
    private final HardLanguageRepository hardLanguageRepository;
    private String uploadDirectory;

    @Transactional
    public CarDTO saveCar(CarDTO carDTO, MultipartFile multipartFile) {
        User user = extractUserFromContext();
        if(user.getIsPremium() || user.getNumberOfAds() < 1) {
            checkForForbiddenWords(carDTO.getDescription());
            savePhoto(multipartFile);
            CarDetails details = new CarDetails();
            details.setBrand(carDTO.getBrand());
            details.setModel(carDTO.getModel());
            details = carDetailsRepository.save(details);

            Car car = new Car();
            car.setCarDetails(details);
            car.setDescription(carDTO.getDescription());
            car.setPhotoPath(multipartFile.getOriginalFilename());
            car.setUser(user);
            Car savedCar = carRepository.save(car);
            user.setNumberOfAds(user.getNumberOfAds() + 1);
            user.getCars().add(savedCar);
            userRepository.save(user);
            return mainMapper.carEntityToDTO(savedCar);
        } else {
            throw new MaximumNumberOfAdsException();
        }
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
        User user = extractUserFromContext();
        Car existingCar = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        if(user.getId().equals(existingCar.getUser().getId()) ||
                user.getRoles().contains(ERole.ROLE_ADMIN.getRole()) ||
                user.getRoles().contains(ERole.ROLE_MANAGER.getRole())) {
            checkForForbiddenWords(carDTO.getDescription());
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
            existingCar.setDescription(carDTO.getDescription());
            Car updatedCar = carRepository.save(existingCar);
            return mainMapper.carEntityToDTO(updatedCar);
        } else {
            throw new ForbiddenAccessToAdvertisementException();
        }
    }

    @Transactional
    public String deleteCar(Long id) {
        User user = extractUserFromContext();
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));

        if(user.getId().equals(car.getUser().getId()) ||
                user.getRoles().contains(ERole.ROLE_ADMIN.getRole()) ||
                user.getRoles().contains(ERole.ROLE_MANAGER.getRole())) {

        carRepository.deleteById(id);
        user.setNumberOfAds(user.getNumberOfAds() - 1);
        user.getCars().remove(car);
        userRepository.save(user);

        return String.format("Car with id %d was deleted", id);
        } else {
            throw new ForbiddenAccessToAdvertisementException();
        }
    }

    public CarDetailsDTO createCarDetails(CarDetailsDTO carDetailsDTO) {
        CarDetails carDetails = new CarDetails(carDetailsDTO.getBrand(), carDetailsDTO.getModel());
        carDetails = carDetailsRepository.save(carDetails);
        return new CarDetailsDTO(carDetails.getBrand(), carDetails.getModel());
    }

    public List<CarDetailsDTO> getAllCarDetails() {
        List<CarDetails> carDetailsList = carDetailsRepository.findAll();
        return carDetailsList.stream().map(carDetails -> new CarDetailsDTO(carDetails.getBrand(), carDetails.getModel())).toList();
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
                System.out.println("Folder created successfully");
            } else {
                System.out.println("Failed to create folder");
            }
        } else {
            System.out.println("Folder already exists");
        }
        uploadDirectory = String.valueOf(folder);
    }

    private User extractUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
    }

    private void checkForForbiddenWords(String description) {
        List<InappropriateWord> forbiddenWords = hardLanguageRepository.findAll();
        Set<String> descriptionWords = Arrays.stream(description.split("\\s+")).collect(Collectors.toSet());
        for(InappropriateWord word: forbiddenWords) {
            if(descriptionWords.contains(word.getContent())) {
                throw new ForbiddenWordException(word.getContent());
            }
        }
    }
}
