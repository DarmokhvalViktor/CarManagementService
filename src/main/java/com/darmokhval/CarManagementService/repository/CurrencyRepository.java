package com.darmokhval.CarManagementService.repository;

import com.darmokhval.CarManagementService.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCcy(String ccy);
}
