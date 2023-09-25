package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.CRUD;
import org.primshits.currency_exchange.dao.ExchangeRatesDAO;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.util.List;

public class ExchangeRatesService implements CRUD<ExchangeRate> {

    private final ExchangeRatesDAO exchangeRatesDAO;

    public ExchangeRatesService() {
        exchangeRatesDAO = new ExchangeRatesDAO();
    }

    @Override
    public List<ExchangeRate> index() {
        return exchangeRatesDAO.index();
    }

    @Override
    public ExchangeRate show(int id) {
        return exchangeRatesDAO.show(id);
    }

    public ExchangeRate show(int baseCurrencyId,int targetCurrencyId){
        return exchangeRatesDAO.show(baseCurrencyId,targetCurrencyId);
    }

    public ExchangeRate show(String baseCurrencyCode,String targetCurrencyCode){
        return exchangeRatesDAO.show(baseCurrencyCode,targetCurrencyCode);
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        exchangeRatesDAO.save(exchangeRate);
    }

    @Override
    public void update(int id, ExchangeRate exchangeRate) {
        exchangeRatesDAO.update(id,exchangeRate);
    }

    @Override
    public void delete(int id) {
        exchangeRatesDAO.delete(id);
    }
}
