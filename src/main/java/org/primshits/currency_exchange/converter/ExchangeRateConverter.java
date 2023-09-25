package org.primshits.currency_exchange.converter;

import org.primshits.currency_exchange.dto.ExchangeRateDTO;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.CurrencyService;

public class ExchangeRateConverter implements Converter<ExchangeRateDTO, ExchangeRate> {
    private final CurrencyService currencyService;
    public ExchangeRateConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public ExchangeRate convert(ExchangeRateDTO source) {
        ExchangeRate exchangeRate = new ExchangeRate();
        Currency baseCurrency = currencyService.show(source.getBaseCurrencyCode());
        Currency targetCurrency = currencyService.show(source.getTargetCurrencyCode());
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
