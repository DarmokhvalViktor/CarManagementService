package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.Advertisement;
import com.darmokhval.CarManagementService.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByLocation(String location);
    List<Advertisement> findByUser(User user);
}
