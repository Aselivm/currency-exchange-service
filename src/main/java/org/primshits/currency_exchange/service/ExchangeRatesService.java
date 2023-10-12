package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.ExchangeRatesDAO;
import org.primshits.currency_exchange.exceptions.NotFoundException;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRatesService {

    private final ExchangeRatesDAO exchangeRatesDAO;

    public ExchangeRatesService() {
        exchangeRatesDAO = new ExchangeRatesDAO();
    }

    public List<ExchangeRate> getAll() {
        return exchangeRatesDAO.index();
    }

    public ExchangeRate get(int id) {
        Optional<ExchangeRate> exchangeRate;
        exchangeRate = exchangeRatesDAO.show(id);
        if (exchangeRate.isPresent()) {
            return exchangeRate.get();
        }
        throw new NotFoundException("No such currency have found");
    }

    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeRate> exchangeRate;
        exchangeRate = exchangeRatesDAO.show(baseCurrencyCode, targetCurrencyCode);

        if (exchangeRate.isPresent()) {
            return exchangeRate.get();
        }

        exchangeRate = calculateExchangeRate(baseCurrencyCode, targetCurrencyCode);

        if (exchangeRate.isPresent()) {
            return exchangeRate.get();
        }
        throw new NotFoundException("No such currency have found");
    }

    public void save(ExchangeRate exchangeRate) {
        exchangeRatesDAO.save(exchangeRate);
    }

    //TODO update only amount
    public void updateRate(String baseCurrencyCode,String targetCurrencyCode, double amount) {
        exchangeRatesDAO.updateRate(baseCurrencyCode,targetCurrencyCode,amount);
    }

    public void delete(int id) throws NotFoundException {
        exchangeRatesDAO.delete(id);
    }

    public ExchangeRate getExchangeRateFromCurrencyPair(String pair) {
        String baseCurrencyCode = pair.substring(0, 3);
        String targetCurrencyCode = pair.substring(3);
        if (baseCurrencyCode.equals(targetCurrencyCode)) return sameCurrencyExchangeRateModelByCode(baseCurrencyCode);
        return get(baseCurrencyCode, targetCurrencyCode);
    }

    private ExchangeRate sameCurrencyExchangeRateModelByCode(String code) {
        Currency currency = new CurrencyService().get(code);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(0);
        exchangeRate.setBaseCurrency(currency);
        exchangeRate.setTargetCurrency(currency);
        exchangeRate.setRate(1);
        return exchangeRate;
    }

    private Optional<ExchangeRate> calculateExchangeRate(String baseCurrencyCode, String targetCurrencyCode){

        Optional<ExchangeRate> exchangeRate;

        exchangeRate = exchangeRatesDAO.show(targetCurrencyCode, baseCurrencyCode);

        if (exchangeRate.isPresent()) {
            ExchangeRate ex = exchangeRate.get();
            ex.setRate(1 / ex.getRate());
            return Optional.of(ex);
        }

        exchangeRate = exchangeRatesDAO.showWithUSDBaseByCode(baseCurrencyCode, targetCurrencyCode);

        return exchangeRate;

    }

}
