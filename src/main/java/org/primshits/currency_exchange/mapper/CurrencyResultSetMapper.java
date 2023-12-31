package org.primshits.currency_exchange.mapper;

import org.primshits.currency_exchange.models.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyResultSetMapper extends ResultSetMapper {
    public static Currency toCurrency(ResultSet resultSet) {
        try {
            return new Currency(
                    resultSet.getInt("ID"),
                    resultSet.getString("Code"),
                    resultSet.getString("FullName"),
                    resultSet.getString("Sign"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Currency toCurrency(String prefix,ResultSet resultSet){
        try {
            return new Currency(
                    resultSet.getInt(prefix+"ID"),
                    resultSet.getString(prefix+"Code"),
                    resultSet.getString(prefix+"FullName"),
                    resultSet.getString(prefix+"Sign"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
