package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.mapper.CurrencyResultSetMapper;
import org.primshits.currency_exchange.mapper.ExchangeRateResultSetMapper;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesDAO extends BaseDAO implements CRUD<ExchangeRate> {


    @Override
    public List<ExchangeRate> index() {
        List<ExchangeRate> exchangeRateList = new LinkedList<>();
        try(Connection connection = connectionBuilder.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ExchangeRate " +
                    "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
                    "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
                    "where ExchangeRate.ID = ?");

            while(resultSet.next()){
                ExchangeRate exchangeRate = ExchangeRateResultSetMapper.toExchangeRate(resultSet);
                exchangeRateList.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exchangeRateList;
    }

    @Override
    public Optional<ExchangeRate> show(int id) {
        ExchangeRate exchangeRate;
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from ExchangeRate " +
                            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
                            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
                            "where ExchangeRate.ID = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return Optional.empty();

            exchangeRate = ExchangeRateResultSetMapper.toExchangeRate(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(exchangeRate);
    }
    public Optional<ExchangeRate> show(int baseCurrencyId, int targetCurrencyId) throws SQLException {
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from ExchangeRate " +
                            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
                            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
                            "WHERE B.ID = ? AND T.ID = ?");
            preparedStatement.setInt(1, baseCurrencyId);
            preparedStatement.setInt(2, targetCurrencyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ExchangeRateResultSetMapper.toExchangeRate(resultSet));
            }

            return null;
        }
    }

    public Optional<ExchangeRate> showWithUSDBaseByCode(String baseCurrencyCode, String targetCurrencyCode) throws SQLException{
        ExchangeRate exchangeRate = new ExchangeRate();
        //TODO
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from ExchangeRate " +
                            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
                            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
                            "WHERE B.Code = 'USD' AND T.Code = ? " +
                            "or B.CODE = 'USD' AND T.Code = ?");
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            ExchangeRate baseAndUSD;
            if (!resultSet.next()) return Optional.empty();
            exchangeRate.setBaseCurrency(CurrencyResultSetMapper.toCurrency("B",resultSet));
            baseAndUSD =  ExchangeRateResultSetMapper.toExchangeRate(resultSet);

            ExchangeRate targetAndUSD;
            if (!resultSet.next()) return Optional.empty();
            exchangeRate.setTargetCurrency(CurrencyResultSetMapper.toCurrency("T",resultSet));
            targetAndUSD =  ExchangeRateResultSetMapper.toExchangeRate(resultSet);

            double rate = baseAndUSD.getRate() / targetAndUSD.getRate();
            exchangeRate.setRate(rate);
            return Optional.of(exchangeRate);
        }

    }

    public Optional<ExchangeRate> show(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from ExchangeRate " +
                            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
                            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
                            "WHERE B.Code = ? AND T.Code = ?");
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ExchangeRateResultSetMapper.toExchangeRate(resultSet));
            }

            return null;
        }
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO ExchangeRate(BaseCurrency,TargetCurrency,Rate) values (?,?,?)");
            preparedStatement.setInt(1,exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2,exchangeRate.getTargetCurrency().getId());
            preparedStatement.setDouble(3,exchangeRate.getRate());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, ExchangeRate exchangeRate) throws SQLException {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ExchangeRate SET Rate = ? where id = ?");
            preparedStatement.setDouble(1,exchangeRate.getRate());
            preparedStatement.setInt(2,exchangeRate.getId());
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM ExchangeRate WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

}
