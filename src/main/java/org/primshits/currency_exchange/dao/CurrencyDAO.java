package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.exceptions.DatabaseException;
import org.primshits.currency_exchange.mapper.CurrencyResultSetMapper;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO extends BaseDAO implements CRUD<Currency> {
    private final String GET_ALL = "select * from Currency";
    private final String GET_BY_ID = "select * from Currency where id = ?";
    private final String GET_BY_CODE = "select * from Currency where Code = ?";
    private final String SAVE = "INSERT INTO Currency(Code,FullName,Sign) values (?,?,?)";
    private final String UPDATE = "UPDATE Currency SET Code = ?, FullName = ?, Sign = ? where id = ?";
    private final String DELETE_BY_ID = "DELETE FROM Currency WHERE id=?";
    private final String DELETE_BY_CODE = "DELETE FROM Currency WHERE code = ?";
    @Override
    public List<Currency> index(){
        List<Currency> currencies = new ArrayList<>();
        try(Connection connection = connectionBuilder.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while(resultSet.next()){
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("ID"));
                currency.setCode(resultSet.getString("Code"));
                currency.setName(resultSet.getString("FullName"));
                currency.setSign(resultSet.getString("Sign"));
                currencies.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

        return currencies;
    }

    @Override
    public Optional<Currency> show(int id){
        try (Connection connection = connectionBuilder.getConnection()){
            return fetchCurrency(GET_BY_ID, String.valueOf(id),connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public Optional<Currency> show(String code){
        try (Connection connection = connectionBuilder.getConnection()){
            return fetchCurrency(GET_BY_CODE, code,connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public Optional<Currency> save(Currency currency){
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SAVE);
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getName());
            preparedStatement.setString(3,currency.getSign());

            preparedStatement.executeUpdate();
            return fetchCurrency(GET_BY_CODE,currency.getCode(),connection);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }


    @Override
    public void update(int id, Currency currency){
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getName());
            preparedStatement.setString(3,currency.getSign());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }


    @Override
    public void delete(int id){
        try(Connection connection = connectionBuilder.getConnection()) {
            connection.createStatement().execute("PRAGMA foreign_keys=ON");
            PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_BY_ID);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }

    }

    public void delete(String code){
        try(Connection connection = connectionBuilder.getConnection()) {
            try {
                connection.createStatement().execute("PRAGMA foreign_keys=ON");
            } catch (SQLException e) {
                throw new DatabaseException();
            }
            PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_BY_CODE);

            preparedStatement.setString(1, code);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    private Optional<Currency> fetchCurrency(String query, String parameter,Connection connection) throws SQLException {
        Currency currency;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, parameter);
        ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currency = CurrencyResultSetMapper.toCurrency(resultSet);
                return Optional.of(currency);
            }
        return Optional.empty();
    }
}
