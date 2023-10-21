package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.ExchangeRatesDAO;
import org.primshits.currency_exchange.dto.ExchangeRateDTO;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ErrorMessage;
import org.primshits.currency_exchange.exceptions.NotFoundException;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.util.ExchangeRateUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import java.util.List;
import java.util.Optional;

public class ExchangeRatesService {

    private final ExchangeRatesDAO exchangeRatesDAO;
    private final CurrencyService currencyService;

    public ExchangeRatesService() {
        exchangeRatesDAO = new ExchangeRatesDAO();
        currencyService = new CurrencyService();
    }

    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRates = exchangeRatesDAO.index();
        if(exchangeRates==null) throw new ApplicationException(ErrorMessage.INTERNAL_DATABASE_ERROR);
        return exchangeRates;
    }

    public ExchangeRate get(int id) {
        return exchangeRatesDAO.show(id).orElseThrow(()->new ApplicationException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND));
    }

    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode) {

        if (baseCurrencyCode.equals(targetCurrencyCode)) return sameCurrencyExchangeRateModelByCode(baseCurrencyCode);

        Optional<ExchangeRate> exchangeRate = exchangeRatesDAO.show(baseCurrencyCode, targetCurrencyCode);
        return exchangeRate
                .orElse(calculateExchangeRate(baseCurrencyCode,targetCurrencyCode)
                        .orElseThrow(()->new ApplicationException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND)
                        ));

    }

    public void save(ExchangeRate exchangeRate) {
        if(!ExchangeRateUtils.isUnique(exchangeRate.getBaseCurrency().getCode(),exchangeRate.getTargetCurrency().getCode())){
            throw new ApplicationException(ErrorMessage.EXCHANGE_RATE_ALREADY_EXISTS);
        }
        exchangeRatesDAO.save(exchangeRate);
    }

    public void updateRate(String baseCurrencyCode,String targetCurrencyCode, double amount) {
        if(exchangeRatesDAO.show(baseCurrencyCode,targetCurrencyCode).isEmpty()){
            throw new ApplicationException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND);
        }
        exchangeRatesDAO.updateRate(baseCurrencyCode,targetCurrencyCode,amount);
    }

    @Deprecated
    public void delete(int id) throws NotFoundException {
        exchangeRatesDAO.delete(id);
    }

    public ExchangeRate getExchangeRateFromCurrencyPair(String pair) {
        String baseCurrencyCode = pair.substring(0, 3);
        String targetCurrencyCode = pair.substring(3);
        return get(baseCurrencyCode, targetCurrencyCode);
    }

    public ExchangeRate convert(ExchangeRateDTO source) {
        ExchangeRate exchangeRate = new ExchangeRate();
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

    public ExchangeRate getOrNull(String baseCurrencyCode,String targetCurrencyCode){
        return exchangeRatesDAO.show(baseCurrencyCode,targetCurrencyCode).orElse(null);
    }

    private ExchangeRate sameCurrencyExchangeRateModelByCode(String code) {
        Currency currency = currencyService.get(code);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(0);
        exchangeRate.setBaseCurrency(currency);
        exchangeRate.setTargetCurrency(currency);
        exchangeRate.setRate(1);
        return exchangeRate;
    }

    private Optional<ExchangeRate> calculateExchangeRate(String baseCurrencyCode, String targetCurrencyCode){
        Optional<ExchangeRate> exchangeRate = exchangeRatesDAO.show(targetCurrencyCode, baseCurrencyCode);

        if (exchangeRate.isPresent()) {
            ExchangeRate ex = exchangeRate.get();
            ex.setRate(1 / ex.getRate());
            return Optional.of(ex);
        }

        exchangeRate = exchangeRatesDAO.showWithUSDBaseByCode(baseCurrencyCode, targetCurrencyCode);

        return exchangeRate;

    }

}
