package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.model.dto.CurrencyDTO;
import com.darmokhval.CarManagementService.model.entity.Currency;
import com.darmokhval.CarManagementService.repository.CurrencyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);
    private static final String PRIVAT_BANK_API_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    private final static RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final CurrencyRepository currencyRepository;

    @Scheduled(cron = "0 0 12 * * *") //runs every day at 12:00
    public void scheduledFetchExchangeRates() {
        try {
            String exchangeRates = restTemplate.getForObject(PRIVAT_BANK_API_URL, String.class);
            List<CurrencyDTO> currencyDTOS = objectMapper.readValue(exchangeRates, new TypeReference<>() {});
            for(CurrencyDTO dto: currencyDTOS) {
                Optional<Currency> existingCurrency = currencyRepository.findByCcy(dto.getCcy());
                if(existingCurrency.isPresent()) {
                    Currency currency = existingCurrency.get();
                    currency.setBuy(dto.getBuy());
                    currency.setSale(dto.getSale());
                    currencyRepository.save(currency);
                } else {
                    currencyRepository.save(new Currency(dto.getCcy(), dto.getBase_ccy(), dto.getSale(), dto.getBuy()));
                }
            }
        } catch (Exception e) {
            log.error("Error fetching exchanging rates, {}", e.getMessage());
        }
    }

    public List<CurrencyDTO> fetchExchangeRates() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies
                .stream()
                .map(currency -> new CurrencyDTO(currency.getCcy(),
                        currency.getBase_ccy(),
                        currency.getBuy(),
                        currency.getSale()))
                .toList();
    }

    @PostConstruct
    public void executeAfterStartup() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::scheduledFetchExchangeRates, 500, TimeUnit.MILLISECONDS);
        executorService.shutdown();
    }
}
