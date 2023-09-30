package org.primshits.currency_exchange.service;

import java.util.List;
import java.util.Optional;

public interface ServiceCRUD <T> {
        List<T> getAll();
        T get(int id);
        void save(T t);
        void update(int id, T t);
        void delete(int id);
}
