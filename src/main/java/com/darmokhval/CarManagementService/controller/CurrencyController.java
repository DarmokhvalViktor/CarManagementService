package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.CurrencyDTO;
import com.darmokhval.CarManagementService.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    public final CurrencyService currencyService;

    @GetMapping("api/currencies")
    public ResponseEntity<List<CurrencyDTO>> fetchExchangeRates() {
        List<CurrencyDTO> dto = currencyService.fetchExchangeRates();
        return ResponseEntity.ok(dto);
    }
}
