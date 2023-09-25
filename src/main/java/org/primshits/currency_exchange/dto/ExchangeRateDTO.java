package org.primshits.currency_exchange.dto;

import lombok.*;
import org.primshits.currency_exchange.models.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTO {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double rate;
}
