package org.primshits.currency_exchange.dao.connection_pool;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    Connection getConnection()throws SQLException;
}
