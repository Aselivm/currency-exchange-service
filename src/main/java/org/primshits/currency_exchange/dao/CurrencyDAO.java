package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO implements CRUD<Currency> {

    private static final String URL = "jdbc:sqlite:A:/sqliteDB/mydatabase.sqlite";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public List<Currency> index() {
        List<Currency> currencies = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Currency");

            while(resultSet.next()){
                Currency currency = new Currency();
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
        Currency currency = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from Currency where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.getResultSet();

            resultSet.next();

            currency = new Currency();

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
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Currency(Code,FullName,Sign) values (?,?,?)");
            preparedStatement.setString(1,currency.getCode());
            preparedStatement.setString(2,currency.getFullName());
            preparedStatement.setString(3,currency.getSign());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(int id, Currency currency) {
        try {
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
        try {
            PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM Currency WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
