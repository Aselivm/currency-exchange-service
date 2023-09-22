package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.CurrencyService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO extends BaseDAO implements CRUD<ExchangeRate> {

    private CurrencyService currencyService;

    public ExchangeRatesDAO() {
        currencyService = new CurrencyService();
    }

    @Override
    public List<ExchangeRate> index() {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ExchangeRates");

            while(resultSet.next()){
                ExchangeRate exchangeRate = new ExchangeRate();

                exchangeRate.setId(resultSet.getInt("ID"));
                exchangeRate.setBaseCurrency(currencyService.show(resultSet.getInt("BaseCurrencyId")));
                exchangeRate.setTargetCurrency(currencyService.show(resultSet.getInt("TargetCurrencyId")));
                exchangeRate.setRate(resultSet.getDouble("Rate"));

                exchangeRateList.add(exchangeRate);
                System.out.println(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exchangeRateList;
    }

    @Override
    public ExchangeRate show(int id) {
        ExchangeRate exchangeRate = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from ExchangeRates where id = ?");
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
        try{

            ExchangeRate exchangeRate = findExchangeRate(connection, baseCurrencyId, targetCurrencyId);

            if (exchangeRate != null) {
                return exchangeRate;
            }

            exchangeRate = findExchangeRate(connection, targetCurrencyId, baseCurrencyId);

            if (exchangeRate != null) {
                exchangeRate.setRate(1 / exchangeRate.getRate());
                return exchangeRate;
            }

            exchangeRate = findExchangeRateOfSameCurrency(baseCurrencyId, targetCurrencyId);

            if(exchangeRate!= null){
                return exchangeRate;
            }

            exchangeRate = findExchangeRateWithEURBase(baseCurrencyId,targetCurrencyId);

            return exchangeRate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO ExchangeRates(BaseCurrency,TargetCurrency,Rate) values (?,?,?)");
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
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ExchangeRates SET Rate = ? where id = ?");
            preparedStatement.setDouble(1,exchangeRate.getRate());
            preparedStatement.setInt(2,exchangeRate.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM ExchangeRates WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private ExchangeRate findExchangeRate(Connection connection, int baseCurrencyId, int targetCurrencyId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ExchangeRates WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?");
        preparedStatement.setInt(1, baseCurrencyId);
        preparedStatement.setInt(2, targetCurrencyId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setBaseCurrency(currencyService.show(baseCurrencyId));
            exchangeRate.setTargetCurrency(currencyService.show(targetCurrencyId));
            exchangeRate.setRate(resultSet.getDouble("Rate"));
            return exchangeRate;
        }

        return null;
    }

    private ExchangeRate findExchangeRateOfSameCurrency(int baseCurrencyId, int targetCurrencyId) throws SQLException {
        if(baseCurrencyId!=targetCurrencyId) return null;
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(0);
        exchangeRate.setBaseCurrency(currencyService.show(baseCurrencyId));
        exchangeRate.setTargetCurrency(currencyService.show(targetCurrencyId));
        exchangeRate.setRate(1);
        return exchangeRate;
    }

    private ExchangeRate findExchangeRateWithEURBase(int baseCurrencyId, int targetCurrencyId) {
        int eurId = currencyService.show("EUR").getId();
        ExchangeRate baseAndEur = show(eurId,baseCurrencyId);
        ExchangeRate targetAndEur = show(eurId,targetCurrencyId);
        double rate = baseAndEur.getRate()/targetAndEur.getRate();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(currencyService.show(baseCurrencyId));
        exchangeRate.setTargetCurrency(currencyService.show(targetCurrencyId));
        exchangeRate.setRate(rate);
        return exchangeRate;
    }
}
