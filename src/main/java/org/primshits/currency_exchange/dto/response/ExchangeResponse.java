package org.primshits.currency_exchange.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.models.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponse {
   private Currency baseCurrency;
   private Currency targetCurrency;
   private double rate;
   private double amount;
   private double convertedAmount;
}
