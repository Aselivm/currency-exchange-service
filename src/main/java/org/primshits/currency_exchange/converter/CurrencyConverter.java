package org.primshits.currency_exchange.converter;

import org.modelmapper.ModelMapper;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.models.Currency;

public class CurrencyConverter implements Converter<CurrencyDTO, Currency>{
    @Override
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

}
