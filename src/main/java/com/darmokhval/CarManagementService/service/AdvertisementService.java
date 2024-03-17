package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.*;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.AveragePriceDTO;
import com.darmokhval.CarManagementService.model.dto.AdvertisementDTO;
import com.darmokhval.CarManagementService.model.dto.CarDetailsDTO;
import com.darmokhval.CarManagementService.model.entity.*;
import com.darmokhval.CarManagementService.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final MainMapper mainMapper;
    private final CarDetailsRepository carDetailsRepository;
    private final HardLanguageRepository hardLanguageRepository;
    private final AdvertisementViewRepository advertisementViewRepository;
    private String uploadDirectory;

    @Transactional
    public AdvertisementDTO saveAdvertisement(AdvertisementDTO advertisementDTO, MultipartFile multipartFile) {

        User user = extractUserFromContext();
        if(user.getIsPremium() || user.getNumberOfAds() < 1) {

            checkForForbiddenWords(advertisementDTO.getDescription());
            savePhoto(multipartFile);

            CarDetails existingDetails = carDetailsRepository.findByBrandAndModel(advertisementDTO.getBrand(), advertisementDTO.getModel());
            if(existingDetails == null) {
                CarDetails details = new CarDetails(advertisementDTO.getBrand(), advertisementDTO.getModel());
                existingDetails = carDetailsRepository.save(details);
            }

            Advertisement advertisement = new Advertisement();
            advertisement.setCarDetails(existingDetails);
            advertisement.setDescription(advertisementDTO.getDescription());
            advertisement.setPhotoPath(multipartFile.getOriginalFilename());
            advertisement.setPrice(advertisementDTO.getPrice());
            advertisement.setLocation(advertisementDTO.getLocation().toLowerCase());
            advertisement.setCurrencyType(advertisementDTO.getCurrencyType());
            advertisement.setUser(user);

            Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
            user.setNumberOfAds(user.getNumberOfAds() != null ? user.getNumberOfAds() + 1 : 1);
            user.getAdvertisements().add(savedAdvertisement);
            userRepository.save(user);
            return mainMapper.advertisementEntityToDTO(savedAdvertisement);
        } else {
            throw new MaximumNumberOfAdsException();
        }
    }

    public List<AdvertisementDTO> getAdvertisements(String location) {
        List<Advertisement> advertisements;
        if(location != null) {
           advertisements = advertisementRepository.findByLocation(location.toLowerCase());
        } else {
            advertisements = advertisementRepository.findAll();
        }
        return advertisements.stream().map(mainMapper::advertisementEntityToDTO).toList();
    }

    public AdvertisementDTO getAdvertisement(Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        User user = extractUserFromContext();
        if(advertisement.isPresent()) {
            AdvertisementView view = new AdvertisementView();
            view.setAdvertisement(advertisement.get());
            view.setViewerId(user.getId());
            view.setTimestamp(LocalDateTime.now());
            advertisement.get().getViews().add(view);
            Advertisement updatedAdvertisement = advertisementRepository.save(advertisement.get());

            return mainMapper.advertisementEntityToDTO(updatedAdvertisement);
        }
        throw new AdvertisementNotFoundException(id);
    }

    public AveragePriceDTO getAveragePrice(String location) {
        AveragePriceDTO averagePriceDTO = new AveragePriceDTO();
        if(location != null) {
            List<Advertisement> advertisements = advertisementRepository.findByLocation(location.toLowerCase());
            averagePriceDTO.setLocation(location.toLowerCase());
            averagePriceDTO.setAveragePrice(advertisements.stream().mapToDouble(Advertisement::getPrice).average().orElse(0));
        } else {
            List<Advertisement> allAdvertisements = advertisementRepository.findAll();
            averagePriceDTO.setAveragePrice(allAdvertisements.stream().mapToDouble(Advertisement::getPrice).average().orElse(0));
        }
        return averagePriceDTO;
    }

    @Transactional
    public AdvertisementDTO updateAdvertisement(Long id, AdvertisementDTO advertisementDTO, Optional<MultipartFile> multipartFile) {
        User user = extractUserFromContext();
        Advertisement existingAdvertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(id));
        if(user.getId().equals(existingAdvertisement.getUser().getId()) ||
                user.getRole().equals(ERole.ROLE_ADMIN.getRole()) ||
                user.getRole().equals(ERole.ROLE_MANAGER.getRole())) {
            checkForForbiddenWords(advertisementDTO.getDescription());
            CarDetails details = existingAdvertisement.getCarDetails();
            if(!advertisementDTO.getBrand().equals(details.getBrand()) || !advertisementDTO.getModel().equals(details.getModel())) {
                details.setBrand(advertisementDTO.getBrand());
                details.setModel(advertisementDTO.getModel());
                carDetailsRepository.save(details);
            }
            multipartFile.ifPresent(file -> {
                savePhoto(file);
                existingAdvertisement.setPhotoPath(file.getOriginalFilename());
            });
            existingAdvertisement.setPrice(advertisementDTO.getPrice());
            existingAdvertisement.setLocation(advertisementDTO.getLocation().toLowerCase());
            existingAdvertisement.setCurrencyType(advertisementDTO.getCurrencyType());
            existingAdvertisement.setDescription(advertisementDTO.getDescription());
            Advertisement updatedAdvertisement = advertisementRepository.save(existingAdvertisement);
            return mainMapper.advertisementEntityToDTO(updatedAdvertisement);
        } else {
            throw new ForbiddenAccessToAdvertisementException();
        }
    }

    @Transactional
    public String deleteAdvertisement(Long id) {
        User user = extractUserFromContext();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(id));

        if(user.getId().equals(advertisement.getUser().getId()) ||
                user.getRole().equals(ERole.ROLE_ADMIN.getRole()) ||
                user.getRole().equals(ERole.ROLE_MANAGER.getRole())) {

        advertisementRepository.deleteById(id);
        user.setNumberOfAds(user.getNumberOfAds() - 1);
        user.getAdvertisements().remove(advertisement);
        userRepository.save(user);

        return String.format("Car with id %d was deleted", id);
        } else {
            throw new ForbiddenAccessToAdvertisementException();
        }
    }

    public Long getViewsForTimePeriod(Long carId, String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = switch (period) {
            case "day" -> now.minusDays(1);
            case "week" -> now.minusWeeks(1);
            case "month" -> now.minusMonths(1);
            default -> throw new InvalidTimePeriodException(period);
        };

        return advertisementViewRepository.countByAdvertisementIdAndTimestampBetween(carId, startTime, now);
    }

    public List<AdvertisementDTO> getMyAdvertisement() {
        User user = extractUserFromContext();
        List<Advertisement> userAdvertisements = advertisementRepository.findByUser(user);
        return userAdvertisements
                .stream()
                .map(mainMapper::advertisementEntityToDTO)
                .toList();
    }

    public CarDetailsDTO createCarDetails(CarDetailsDTO carDetailsDTO) {
        CarDetails existingDetails = carDetailsRepository.findByBrandAndModel(carDetailsDTO.getBrand(), carDetailsDTO.getModel());
        if(existingDetails == null) {
            CarDetails details = new CarDetails(carDetailsDTO.getBrand(), carDetailsDTO.getModel());
            existingDetails = carDetailsRepository.save(details);
        }
        return new CarDetailsDTO(existingDetails.getBrand(), existingDetails.getModel());
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
