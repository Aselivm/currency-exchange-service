package org.primshits.currency_exchange.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.service.CurrencyService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyUtils {
    private static final CurrencyService currencyService = new CurrencyService();

    public static boolean isValid(String code, String name, String sign) {
        return code.length() == 3 && name.length() < 100 && sign.length() < 5;
    }
    public static boolean isUnique(String code){
        Currency currency = currencyService.getOrNull(code);
        return currency==null;
    }

}
