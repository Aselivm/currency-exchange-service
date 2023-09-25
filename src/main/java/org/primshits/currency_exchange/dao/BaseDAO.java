package org.primshits.currency_exchange.dao;


import org.primshits.currency_exchange.dao.connection_pool.ConnectionBuilder;
import org.primshits.currency_exchange.dao.connection_pool.HikariConnectionPool;

public abstract class BaseDAO {
    protected ConnectionBuilder connectionBuilder;
    public BaseDAO() {
        this.connectionBuilder = new HikariConnectionPool();
    }
}
