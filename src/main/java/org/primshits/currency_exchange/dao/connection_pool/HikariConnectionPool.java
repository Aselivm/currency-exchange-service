package org.primshits.currency_exchange.dao.connection_pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.primshits.currency_exchange.dao.connection_pool.ConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionPool implements ConnectionBuilder {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:A:/sqliteDB/mydatabase.sqlite");
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(100);

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}