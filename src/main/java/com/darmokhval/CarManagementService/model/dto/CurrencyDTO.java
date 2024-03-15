package com.darmokhval.CarManagementService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDTO {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;

    public CurrencyDTO(String ccy, String base_ccy, String buy, String sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }
}
