package org.primshits.currency_exchange.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.ExchangeRatesService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExchangeRateUtils {
    private static final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    public static boolean isValid(String baseCurrencyCode, String targetCurrencyCode){
        return baseCurrencyCode.length()==3 || targetCurrencyCode.length()==3;
    }

    public static boolean isUnique(String baseCurrencyCode,String targetCurrencyCode){
        ExchangeRate exchangeRate = exchangeRatesService.getOrNull(baseCurrencyCode,targetCurrencyCode);
        return exchangeRate == null;
    }
}
