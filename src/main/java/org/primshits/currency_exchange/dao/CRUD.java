package org.primshits.currency_exchange.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUD<T> {
    List<T> index() throws SQLException;

    Optional<T> show(int id) throws SQLException;
    void save(T t) throws SQLException;

    void update(int id, T t) throws SQLException;

    void delete(int id) throws SQLException;
    //TODO перенести DTO convert в service
    //TODO в DAO слишком много методов которые нужно поместить в сервис, например те что высчитывают курс
    //TODO Класс StringUtils считывающий ввод пользователя и кидающий исключения
    //TODO Деплой
    //TODO в контроллере мусор по папкам раскидать
    //TODO подключить front к проекту.
}
