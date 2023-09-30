package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.converter.ExchangeRateConverter;
import org.primshits.currency_exchange.dao.ExchangeRatesDAO;
import org.primshits.currency_exchange.exceptions.NotFoundException;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesService implements ServiceCRUD<ExchangeRate> {

    private final ExchangeRatesDAO exchangeRatesDAO;
    private final ExchangeRateConverter exchangeRateConverter;
    public ExchangeRatesService() {
        exchangeRatesDAO = new ExchangeRatesDAO();
        exchangeRateConverter = new ExchangeRateConverter();
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRatesDAO.index();
    }

    @Override
    public ExchangeRate get(int id) {
        return exchangeRatesDAO.show(id).get();
    }

    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeRate> exchangeRate;
        try {
            if(baseCurrencyCode.equals(targetCurrencyCode)) return sameCurrencyExchangeRateModelByCode(baseCurrencyCode);
            exchangeRate = exchangeRatesDAO.show(baseCurrencyCode,targetCurrencyCode);

                if (exchangeRate.isPresent()){
                    return exchangeRate.get();
                }

                exchangeRate = calculateExchangeRate(baseCurrencyCode,targetCurrencyCode);

                if(exchangeRate.isPresent()){
                    return exchangeRate.get();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        throw new NotFoundException("No such currency have found");
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        exchangeRatesDAO.save(exchangeRate);
    }

    @Override
    public void update(int id, ExchangeRate exchangeRate) {
        exchangeRatesDAO.update(id, exchangeRate);
    }

    @Override
    public void delete(int id) {
        exchangeRatesDAO.delete(id);
    }

    private Optional<ExchangeRate> calculateExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {

        Optional<ExchangeRate> exchangeRate;

        exchangeRate = exchangeRatesDAO.show(targetCurrencyCode, baseCurrencyCode);

        if (exchangeRate.isPresent()) {
            ExchangeRate ex = exchangeRate.get();
            ex.setRate(1 / ex.getRate());
            return Optional.of(ex);
        }

        exchangeRate = exchangeRatesDAO.showWithUSDBaseByCode(baseCurrencyCode,targetCurrencyCode);

        return exchangeRate;

    }

    private ExchangeRate sameCurrencyExchangeRateModelByCode(String code){
        return exchangeRateConverter.sameCurrencyExchangeRateModelByCode(code);
    }


}
