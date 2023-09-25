package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.CurrencyService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO extends BaseDAO implements CRUD<ExchangeRate> {

    private final CurrencyService currencyService;

    public ExchangeRatesDAO() {
        currencyService = new CurrencyService();
    }

    @Override
    public List<ExchangeRate> index() {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        try(Connection connection = connectionBuilder.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ExchangeRate");

            while(resultSet.next()){
                ExchangeRate exchangeRate = new ExchangeRate();

                exchangeRate.setId(resultSet.getInt("ID"));
                exchangeRate.setBaseCurrency(currencyService.show(resultSet.getInt("BaseCurrencyId")));
                exchangeRate.setTargetCurrency(currencyService.show(resultSet.getInt("TargetCurrencyId")));
                exchangeRate.setRate(resultSet.getDouble("Rate"));

                exchangeRateList.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exchangeRateList;
    }

    @Override
    public ExchangeRate show(int id) {
        ExchangeRate exchangeRate;
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from ExchangeRate where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;

            exchangeRate = new ExchangeRate();
            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setBaseCurrency(currencyService.show(resultSet.getInt("BaseCurrencyId")));
            exchangeRate.setTargetCurrency(currencyService.show(resultSet.getInt("TargetCurrencyId")));
            exchangeRate.setRate(resultSet.getDouble("Rate"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    public ExchangeRate show(int baseCurrencyId, int targetCurrencyId) {
        Currency baseCurrency = currencyService.show(baseCurrencyId);
        Currency targetCurrency = currencyService.show(targetCurrencyId);
        return showExchangeRate(baseCurrency, targetCurrency);
    }

    public ExchangeRate show(String baseCurrencyCode, String targetCurrencyCode) {
        Currency baseCurrency = currencyService.show(baseCurrencyCode);
        Currency targetCurrency = currencyService.show(targetCurrencyCode);
        return showExchangeRate(baseCurrency, targetCurrency);
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO ExchangeRate(BaseCurrency,TargetCurrency,Rate) values (?,?,?)");
            preparedStatement.setInt(1,exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2,exchangeRate.getTargetCurrency().getId());
            preparedStatement.setDouble(3,exchangeRate.getRate());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, ExchangeRate exchangeRate) {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ExchangeRate SET Rate = ? where id = ?");
            preparedStatement.setDouble(1,exchangeRate.getRate());
            preparedStatement.setInt(2,exchangeRate.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM ExchangeRate WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private ExchangeRate showExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        try (Connection connection = connectionBuilder.getConnection()) {
            ExchangeRate exchangeRate = findExchangeRate(connection, baseCurrency, targetCurrency);

            if (exchangeRate != null) {
                return exchangeRate;
            }

            exchangeRate = findExchangeRate(connection, targetCurrency, baseCurrency);

            if (exchangeRate != null) {
                exchangeRate.setRate(1 / exchangeRate.getRate());
                return exchangeRate;
            }

            exchangeRate = findExchangeRateOfSameCurrency(baseCurrency, targetCurrency);

            if (exchangeRate != null) {
                return exchangeRate;
            }

            exchangeRate = findExchangeRateWithEURBase(baseCurrency, targetCurrency);

            return exchangeRate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate findExchangeRate(Connection connection, Currency baseCurrency, Currency targetCurrency) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ExchangeRate WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?");
        preparedStatement.setInt(1, baseCurrency.getId());
        preparedStatement.setInt(2, targetCurrency.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setBaseCurrency(baseCurrency);
            exchangeRate.setTargetCurrency(targetCurrency);
            exchangeRate.setRate(resultSet.getDouble("Rate"));
            return exchangeRate;
        }

        return null;
    }

    private ExchangeRate findExchangeRateOfSameCurrency(Currency baseCurrency, Currency targetCurrency) throws SQLException {
        if(baseCurrency!=targetCurrency) return null;
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(0);
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(1);
        return exchangeRate;
    }

    private ExchangeRate findExchangeRateWithEURBase(Currency baseCurrency, Currency targetCurrency) {
        ExchangeRate baseAndEur = show("EUR",baseCurrency.getCode());
        ExchangeRate targetAndEur = show("EUR",targetCurrency.getCode());
        double rate = targetAndEur.getRate()/baseAndEur.getRate();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(baseCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(rate);
        return exchangeRate;
    }
}
