package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.exceptions.DatabaseException;
import org.primshits.currency_exchange.mapper.CurrencyResultSetMapper;
import org.primshits.currency_exchange.mapper.ExchangeRateResultSetMapper;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesDAO extends BaseDAO implements CRUD<ExchangeRate> {

    private final String GET_ALL = "select " +
            "ExchangeRate.ID, ExchangeRate.Rate, B.ID as baseID," +
            "B.Code as baseCode,B.FullName as baseFullName,B.Sign as baseSign," +
            "T.ID as targetID,T.Code as targetCode,T.FullName as targetFullName,T.Sign as targetSign " +
            " from ExchangeRate " +
            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID ";
    private final String GET_BY_ID = "select " +
            "ExchangeRate.ID, ExchangeRate.Rate, B.ID as baseID," +
            "B.Code as baseCode,B.FullName as baseFullName,B.Sign as baseSign," +
            "T.ID as targetID,T.Code as targetCode,T.FullName as targetFullName,T.Sign as targetSign " +
            " from ExchangeRate " +
            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
            "where ExchangeRate.ID = ?";
    private final String GET_BY_CURRENCY_IDS = "select " +
            "ExchangeRate.ID, ExchangeRate.Rate, B.ID as baseID," +
            "B.Code as baseCode,B.FullName as baseFullName,B.Sign as baseSign," +
            "T.ID as targetID,T.Code as targetCode,T.FullName as targetFullName,T.Sign as targetSign " +
            " from ExchangeRate " +
            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
            "WHERE B.ID = ? AND T.ID = ?";
    private final String GET_BY_CURRENCY_CODES = "select " +
            "ExchangeRate.ID, ExchangeRate.Rate, B.ID as baseID," +
            "B.Code as baseCode,B.FullName as baseFullName,B.Sign as baseSign," +
            "T.ID as targetID,T.Code as targetCode,T.FullName as targetFullName,T.Sign as targetSign " +
            " from ExchangeRate " +
            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
            "WHERE B.Code = ? AND T.Code = ?";
    private final String GET_CROSS_RATE_THROUGH_USD = "select " +
            "ExchangeRate.ID, ExchangeRate.Rate, B.ID as baseID," +
            "B.Code as baseCode,B.FullName as baseFullName,B.Sign as baseSign," +
            "T.ID as targetID,T.Code as targetCode,T.FullName as targetFullName,T.Sign as targetSign " +
            " from ExchangeRate " +
            "join Currency B on ExchangeRate.BaseCurrencyId = B.ID " +
            "join Currency T on ExchangeRate.TargetCurrencyId = T.ID " +
            "WHERE B.Code = 'USD' AND T.Code = ? " +
            "or B.Code = 'USD' AND T.Code = ?";
    private final String SAVE = "INSERT INTO ExchangeRate(BaseCurrencyID,TargetCurrencyID,Rate) values (?,?,?)";

    private final String UPDATE_RATE = "update ExchangeRate set Rate = ? " +
            "where BaseCurrencyID = (SELECT (ID) FROM Currency where Code = ?)" +
            "and TargetCurrencyID = (SELECT (ID) FROM Currency where Code = ?)" +
            "OR BaseCurrencyID = (SELECT (ID) FROM Currency where Code = ?)" +
            "and TargetCurrencyID = (SELECT (ID) FROM Currency where Code = ?)";

    private final String DELETE_BY_ID = "DELETE FROM ExchangeRate WHERE id=?";

    @Override
    public List<ExchangeRate> index(){
        List<ExchangeRate> exchangeRateList = new LinkedList<>();
        try(Connection connection = connectionBuilder.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while(resultSet.next()){
                ExchangeRate exchangeRate = ExchangeRateResultSetMapper.toExchangeRate(resultSet);
                exchangeRateList.add(exchangeRate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return exchangeRateList;
    }

    @Override
    public Optional<ExchangeRate> show(int id){
        ExchangeRate exchangeRate;
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return Optional.empty();

            exchangeRate = ExchangeRateResultSetMapper.toExchangeRate(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return Optional.of(exchangeRate);
    }

    public Optional<ExchangeRate> show(int baseCurrencyId, int targetCurrencyId){
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CURRENCY_IDS);
            preparedStatement.setInt(1, baseCurrencyId);
            preparedStatement.setInt(2, targetCurrencyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ExchangeRateResultSetMapper.toExchangeRate(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public Optional<ExchangeRate> show(String baseCurrencyCode, String targetCurrencyCode){
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_BY_CURRENCY_CODES);
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ExchangeRateResultSetMapper.toExchangeRate(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public Optional<ExchangeRate> showWithUSDBaseByCode(String baseCurrencyCode, String targetCurrencyCode){
        ExchangeRate exchangeRate = new ExchangeRate();
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CROSS_RATE_THROUGH_USD);
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            ExchangeRate baseAndUSD;
            if (!resultSet.next()) return Optional.empty();
            exchangeRate.setBaseCurrency(
                    CurrencyResultSetMapper.toCurrency("base",resultSet));
            baseAndUSD =  ExchangeRateResultSetMapper.toExchangeRate(resultSet);

            ExchangeRate targetAndUSD;
            if (!resultSet.next()) return Optional.empty();
            exchangeRate.setTargetCurrency(
                    CurrencyResultSetMapper.toCurrency("target",resultSet));
            targetAndUSD =  ExchangeRateResultSetMapper.toExchangeRate(resultSet);

            double rate = baseAndUSD.getRate() / targetAndUSD.getRate();
            exchangeRate.setRate(rate);
            return Optional.of(exchangeRate);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

    }

    @Override
    public Optional<ExchangeRate> save(ExchangeRate exchangeRate){
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            (SAVE);
            preparedStatement.setInt(1,exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2,exchangeRate.getTargetCurrency().getId());
            preparedStatement.setDouble(3,exchangeRate.getRate());

            preparedStatement.executeUpdate();

            return
                    getExchangeRateByCodes(
                            exchangeRate.getBaseCurrency().getCode(),
                            exchangeRate.getTargetCurrency().getCode(),
                            connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    @Deprecated
    public void update(int id, ExchangeRate exchangeRate){
    }
    public Optional<ExchangeRate> updateRate(String baseCurrencyCode,String targetCurrencyCode, double rate){
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement(UPDATE_RATE);
            preparedStatement.setDouble(1,rate);

            preparedStatement.setString(2,baseCurrencyCode);
            preparedStatement.setString(3,targetCurrencyCode);

            preparedStatement.setString(4,targetCurrencyCode);
            preparedStatement.setString(5,baseCurrencyCode);
            preparedStatement.executeUpdate();

            return getExchangeRateByCodes(baseCurrencyCode,targetCurrencyCode,connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public void delete(int id){
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_BY_ID);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    private Optional<ExchangeRate> getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode,Connection connection) throws SQLException {
        PreparedStatement selectStatement = connection.prepareStatement(GET_BY_CURRENCY_CODES);
        selectStatement.setString(1,baseCurrencyCode);
        selectStatement.setString(2,targetCurrencyCode);
        ResultSet resultSet = selectStatement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(ExchangeRateResultSetMapper.toExchangeRate(resultSet));
        }
        return Optional.empty();
    }

}
