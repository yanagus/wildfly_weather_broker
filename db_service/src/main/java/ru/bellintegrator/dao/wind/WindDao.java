package ru.bellintegrator.dao.wind;

import ru.bellintegrator.model.Wind;

/**
 * DAO для работы с Wind
 */
public interface WindDao {

    /**
     * Сохранить текущую информацию о ветре в базу данных
     *
     * @param wind текущая информация о ветре
     */
    void save(Wind wind);
}
