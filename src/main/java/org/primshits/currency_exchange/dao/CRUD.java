package org.primshits.currency_exchange.dao;

import java.util.List;

public interface CRUD<T> {
    List<T> index();

    T show(int id);
    void save(T t);

    void update(int id, T t);

    void delete(int id);
    //TODO Класс StringUtils считывающий ввод пользователя и кидающий исключения
    //TODO Деплой
    //TODO в контроллере мусор по папкам раскидать
    //TODO подключить front к проекту.
}
