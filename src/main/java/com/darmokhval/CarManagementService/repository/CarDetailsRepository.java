package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
}
