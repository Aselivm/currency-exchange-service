package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.CRUD;
import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.models.Currency;

import java.util.List;

public class CurrencyService implements CRUD<Currency> {
    private final CurrencyDAO currencyDAO;
    public CurrencyService() {
        currencyDAO = new CurrencyDAO();
    }

    @Override
    public List<Currency> index() {
        return currencyDAO.index();
    }

    @Override
    public Currency show(int id) {
        return currencyDAO.show(id);
    }

    public Currency show(String code) {
        return currencyDAO.show(code);
    }

    @Override
    public void save(Currency currency) {
        currencyDAO.save(currency);
    }

    @Override
    public void update(int id, Currency currency) {
        currencyDAO.update(id,currency);
    }

    @Override
    public void delete(int id) {
        currencyDAO.delete(id);

    }

    public void delete(String code) {
        currencyDAO.delete(code);
    }
}
