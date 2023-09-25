package org.primshits.currency_exchange.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private double rate;
}
