package org.primshits.currency_exchange.dao;

import org.primshits.currency_exchange.models.ExchangeRate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUD<T> {
    List<T> index() throws SQLException;

    Optional<T> show(int id) throws SQLException;
    Optional<T> save(T t) throws SQLException;

    void update(int id, T t) throws SQLException;

    void delete(int id) throws SQLException;
}
