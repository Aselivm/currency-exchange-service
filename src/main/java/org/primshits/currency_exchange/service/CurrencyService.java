package org.primshits.currency_exchange.service;

import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.exceptions.*;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.util.CurrencyUtils;

import java.util.List;
import java.util.Optional;

public class CurrencyService {
    private final CurrencyDAO currencyDAO;

    public CurrencyService() {
        currencyDAO = new CurrencyDAO();
    }

    public List<Currency> getAll(){
        List<Currency> currencyList = null;
        currencyList = currencyDAO.index();
        if (currencyList == null) {
            throw new NotFoundException("Currency table not found");
        }
        return currencyList;
    }

    public Currency get(int id){
        Optional<Currency> currency = null;
        currency = currencyDAO.show(id);
        if (currency.isEmpty()) {
            throw new NotFoundException("Currency not found");
        }
        return currency.get();
    }

    public Currency getOrNull(String code) {
        Optional<Currency> currency;
        currency = currencyDAO.show(code);
        if (currency.isEmpty()) {
            return null;
        }
        return currency.get();
    }

    public Currency get(String code){
        Optional<Currency> currency;
        currency = currencyDAO.show(code);
        if (currency.isEmpty()) {
            throw new NotFoundException("Currency not found");
        }
        return currency.orElseThrow(() -> new ApplicationException(ErrorMessage.CURRENCY_NOT_FOUND));
    }

    public void save(Currency currency){
        if (CurrencyUtils.isUnique(currency.getCode())) {
            currencyDAO.save(currency);
        }
        throw new ApplicationException(ErrorMessage.CURRENCY_ALREADY_EXISTS);
    }

    public void updateAmount(int id, Currency currency){
        currencyDAO.update(id,currency);
    }

    public void delete(int id) {
        currencyDAO.delete(id);

    }

    public void delete(String code) {
        currencyDAO.delete(code);
    }
}
