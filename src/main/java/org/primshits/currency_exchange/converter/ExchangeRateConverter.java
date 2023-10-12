package org.primshits.currency_exchange.converter;

import org.primshits.currency_exchange.dto.ExchangeRateDTO;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.CurrencyService;

public class ExchangeRateConverter implements Converter<ExchangeRateDTO, org.primshits.currency_exchange.models.ExchangeRate> {
    private final CurrencyService currencyService;
    public ExchangeRateConverter() {
        this.currencyService = new CurrencyService();
    }

    @Override
    public org.primshits.currency_exchange.models.ExchangeRate convert(ExchangeRateDTO source) {
        org.primshits.currency_exchange.models.ExchangeRate exchangeRate = new org.primshits.currency_exchange.models.ExchangeRate();
        Currency baseCurrency = currencyService.get(source.getBaseCurrencyCode());
        Currency targetCurrency = currencyService.get(source.getTargetCurrencyCode());
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(source.getRate());
        return exchangeRate;
    }

    public ExchangeRateDTO putToDTO(String baseCurrencyCode, String targetCurrencyCode, double rate) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setBaseCurrencyCode(baseCurrencyCode);
        exchangeRateDTO.setTargetCurrencyCode(targetCurrencyCode);
        exchangeRateDTO.setRate(rate);
        return exchangeRateDTO;
    }
}
