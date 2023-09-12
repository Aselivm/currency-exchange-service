package org.primshits.currency_exchange.dao;

import java.util.List;

public interface CRUD<T> {
    List<T> index();

    T show(int id);


    void save(T t);

    void update(int id, T t);

    void delete(int id);

}
