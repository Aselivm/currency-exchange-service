package org.primshits.currency_exchange.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double rate;
}