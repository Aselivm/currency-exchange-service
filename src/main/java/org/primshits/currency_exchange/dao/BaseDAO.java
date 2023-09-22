package org.primshits.currency_exchange.dao;

import java.sql.*;

import java.sql.SQLException;

public abstract class BaseDAO {
    protected Connection connection;

    {
        try {
            connection = HikariConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
