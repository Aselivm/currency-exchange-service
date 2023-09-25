package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO extends BaseDAO implements CRUD<Currency> {
    @Override
    public List<Currency> index() {
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
            throw new RuntimeException(e);
        }

        return currencies;
    }

    @Override
    public Currency show(int id) {
        Currency currency;
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from Currency where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            currency = new Currency();

            currency.setId(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("Code"));
            currency.setFullName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    public Currency show(String code) {
        Currency currency;
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from Currency where Code = ?");
            preparedStatement.setString(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;

            currency = new Currency();
            currency.setId(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("Code"));
            currency.setFullName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    @Override
    public void save(Currency currency) {
        try(Connection connection = connectionBuilder.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Currency(Code,FullName,Sign) values (?,?,?)");
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getFullName());
            preparedStatement.setString(3,currency.getSign());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(int id, Currency currency) {
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Currency SET Code = ?, FullName = ?, Sign = ? where id = ?");
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getFullName());
            preparedStatement.setString(3,currency.getSign());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int id) {
        try(Connection connection = connectionBuilder.getConnection()) {
            connection.createStatement().execute("PRAGMA foreign_keys=ON");
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM Currency WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(String code) {
        try(Connection connection = connectionBuilder.getConnection()) {
            connection.createStatement().execute("PRAGMA foreign_keys=ON");
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM Currency WHERE code = ?");

            preparedStatement.setString(1, code);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
