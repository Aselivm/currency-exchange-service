package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.service.CurrencyService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO implements CRUD<ExchangeRate> {

    private CurrencyService currencyService;

    public ExchangeRatesDAO() {
        currencyService = new CurrencyService();
    }

    private static final String URL = "jdbc:sqlite:A:/sqliteDB/mydatabase.sqlite";

    static {
        try {
            Class.forName("org.primshits.currency_exchange.dao.CurrencyDAO");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<ExchangeRate> index() {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ExchangeRates");

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
        ExchangeRate exchangeRate = null;
        try(Connection connection = DriverManager.getConnection(URL)) {
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

    @Override
    public void save(ExchangeRate exchangeRate) {
        try(Connection connection = DriverManager.getConnection(URL)) {
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
        try(Connection connection = DriverManager.getConnection(URL)) {
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
        try(Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM ExchangeRates WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
