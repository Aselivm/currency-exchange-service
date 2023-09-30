package org.primshits.currency_exchange.converter;

import org.primshits.currency_exchange.dto.ExchangeRate;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.service.CurrencyService;

public class ExchangeRateConverter implements Converter<ExchangeRate, org.primshits.currency_exchange.models.ExchangeRate> {
    private final CurrencyService currencyService;
    public ExchangeRateConverter() {
        this.currencyService = new CurrencyService();
    }

    @Override
    public org.primshits.currency_exchange.models.ExchangeRate convert(ExchangeRate source) {
        org.primshits.currency_exchange.models.ExchangeRate exchangeRate = new org.primshits.currency_exchange.models.ExchangeRate();
        Currency baseCurrency = currencyService.get(source.getBaseCurrencyCode());
        Currency targetCurrency = currencyService.get(source.getTargetCurrencyCode());
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(source.getRate());
        return exchangeRate;
    }

    public ExchangeRate putToDTO(String baseCurrencyCode, String targetCurrencyCode, double rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrencyCode(baseCurrencyCode);
        exchangeRate.setTargetCurrencyCode(targetCurrencyCode);
        exchangeRate.setRate(rate);
        return exchangeRate;
    }

    public org.primshits.currency_exchange.models.ExchangeRate sameCurrencyExchangeRateModelByCode(String code){
        Currency currency = currencyService.get(code);
        org.primshits.currency_exchange.models.ExchangeRate exchangeRate = new org.primshits.currency_exchange.models.ExchangeRate();
        exchangeRate.setId(0);
        exchangeRate.setBaseCurrency(currency);
        exchangeRate.setTargetCurrency(currency);
        exchangeRate.setRate(1);
        return exchangeRate;
    }
}
