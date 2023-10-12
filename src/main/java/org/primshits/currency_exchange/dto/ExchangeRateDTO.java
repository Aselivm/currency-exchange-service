package org.primshits.currency_exchange.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTO {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double rate;
}