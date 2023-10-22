package org.primshits.currency_exchange.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ErrorMessage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {
    public static void validateCurrency(String code, String name, String sign) {
        if (InputStringUtils.isEmptyField(code, name, sign) || !CurrencyUtils.isValid(code, name, sign) || code.length() != 3) {
            throw new ApplicationException(ErrorMessage.INVALID_CURRENCY_INPUT);
        }
    }

    public static void validateCurrency(String code){
        if (code.length() != 3) {
            throw new ApplicationException(ErrorMessage.BAD_CURRENCY_CODE_REQUEST);
        }
    }

    public static void validateExchange(String baseCurrencyCode, String targetCurrencyCode, String digit){
        if(InputStringUtils.isEmptyField(baseCurrencyCode,targetCurrencyCode,digit)||!ExchangeRateUtils.isValid(baseCurrencyCode,targetCurrencyCode)){
            throw new ApplicationException(ErrorMessage.INVALID_EXCHANGE_RATE_INPUT);
        }
    }

    public static void validateExchangeRate(String pair){
        if(pair.length()!=6){
            throw new ApplicationException(ErrorMessage.INVALID_EXCHANGE_RATE_INPUT);
        }
    }

}