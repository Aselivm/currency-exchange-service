package org.primshits.currency_exchange.service;

import org.modelmapper.ModelMapper;
import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.exceptions.*;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.util.CurrencyUtils;

import java.util.List;

public class CurrencyService {
    private final CurrencyDAO currencyDAO;

    public CurrencyService() {
        currencyDAO = new CurrencyDAO();
    }

    public List<Currency> getAll(){
        List<Currency> currencyList = currencyDAO.index();
        if (currencyList == null) {
            throw new ApplicationException(ErrorMessage.INTERNAL_DATABASE_ERROR);
        }
        return currencyList;
    }

    public Currency get(int id){
        return currencyDAO.show(id).orElseThrow(()->new ApplicationException(ErrorMessage.CURRENCY_NOT_FOUND));
    }

    public Currency get(String code){
        return currencyDAO.show(code).orElseThrow(() -> new ApplicationException(ErrorMessage.CURRENCY_NOT_FOUND));
    }

    public Currency getOrNull(String code) {
        return currencyDAO.show(code)
                .orElse(null);
    }

    public void save(Currency currency){
        if (!CurrencyUtils.isUnique(currency.getCode())) {
            throw new ApplicationException(ErrorMessage.CURRENCY_ALREADY_EXISTS);
        }
        currencyDAO.save(currency);
    }

    public Currency convert(CurrencyDTO source) {
        return new ModelMapper().map(source,Currency.class);
    }

    public CurrencyDTO putToDTO(String name, String code, String sign){
        CurrencyDTO currency = new CurrencyDTO();
        currency.setFullName(name);
        currency.setCode(code);
        currency.setSign(sign);
        return currency;
    }


    @Deprecated
    public void update(int id, Currency currency){
        currencyDAO.update(id,currency);
    }

    @Deprecated
    public void delete(int id) {
        currencyDAO.delete(id);

    }

    @Deprecated
    public void delete(String code) {
        currencyDAO.delete(code);
    }
}
