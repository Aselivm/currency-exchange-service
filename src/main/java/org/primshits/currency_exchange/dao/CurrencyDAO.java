package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.exceptions.DatabaseException;
import org.primshits.currency_exchange.mapper.CurrencyResultSetMapper;
import org.primshits.currency_exchange.models.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO extends BaseDAO implements CRUD<Currency> {
    @Override
    public List<Currency> index(){
        List<Currency> currencies = new ArrayList<>();
        try(Connection connection = connectionBuilder.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Currency");

            while(resultSet.next()){
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("ID"));
                currency.setCode(resultSet.getString("Code"));
                currency.setFullName(resultSet.getString("FullName"));
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
        return fetchCurrency("select * from Currency where id = ?", String.valueOf(id));
    }

    public Optional<Currency> show(String code){
        return fetchCurrency("select * from Currency where Code = ?", code);
    }

    @Override
    public void save(Currency currency){
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Currency(Code,FullName,Sign) values (?,?,?)");
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getFullName());
            preparedStatement.setString(3,currency.getSign());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }


    @Override
    public void update(int id, Currency currency){
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Currency SET Code = ?, FullName = ?, Sign = ? where id = ?");
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getFullName());
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
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM Currency WHERE id=?");

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
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM Currency WHERE code = ?");

            preparedStatement.setString(1, code);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    private Optional<Currency> fetchCurrency(String query, String parameter){
        Currency currency;
        try (Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currency = CurrencyResultSetMapper.toCurrency(resultSet);
                return Optional.of(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return Optional.empty();
    }
}
