package org.primshits.currency_exchange.mapper;

import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateResultSetMapper extends ResultSetMapper {
    public static ExchangeRate toExchangeRate(ResultSet resultSet){
        try {
            ExchangeRate exchangeRate = new ExchangeRate();
            Currency baseCurrency = CurrencyResultSetMapper.toCurrency("B",resultSet);
            Currency targetCurrency = CurrencyResultSetMapper.toCurrency("T",resultSet);

            exchangeRate.setId(resultSet.getInt("ExchangeRate.ID"));
            exchangeRate.setBaseCurrency(baseCurrency);
            exchangeRate.setTargetCurrency(targetCurrency);
            exchangeRate.setRate(resultSet.getDouble("Rate"));
            return exchangeRate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
