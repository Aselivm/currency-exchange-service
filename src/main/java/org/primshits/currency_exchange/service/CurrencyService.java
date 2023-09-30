package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.exceptions.DatabaseException;
import org.primshits.currency_exchange.exceptions.NotFoundException;
import org.primshits.currency_exchange.models.Currency;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CurrencyService implements ServiceCRUD<Currency> {
    private final CurrencyDAO currencyDAO;
    public CurrencyService() {
        currencyDAO = new CurrencyDAO();
    }

    @Override
    public List<Currency> getAll() {
        List<Currency> currencyList = null;
        try {
            currencyList = currencyDAO.index();
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
        if(currencyList==null){
            throw new NotFoundException("Currency table not found");
        }
            return currencyList;
    }

    @Override
    public Currency get(int id) {
        Optional<Currency> currency = null;
        try {
            currency = currencyDAO.show(id);
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
        if (currency.isEmpty()){
            throw new NotFoundException("Currency not found");
        }
        return currency.get();
    }

    public Currency get(String code) {
        Optional<Currency> currency = null;
        try {
            currency = currencyDAO.show(code);
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
        if (currency.isEmpty()){
            throw new NotFoundException("Currency not found");
        }
        return currency.get();
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
