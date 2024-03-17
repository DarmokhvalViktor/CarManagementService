package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.AdvertisementView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdvertisementViewRepository extends JpaRepository<AdvertisementView, Long> {
    long countByAdvertisementIdAndTimestampBetween(Long carId, LocalDateTime startTimestamp, LocalDateTime endTimestamp);
}
