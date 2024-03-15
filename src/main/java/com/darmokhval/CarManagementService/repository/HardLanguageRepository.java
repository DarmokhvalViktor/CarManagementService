package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.InappropriateWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardLanguageRepository extends JpaRepository<InappropriateWord, Long> {
}
