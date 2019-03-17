package ru.bellintegrator.dao.astronomy;

import ru.bellintegrator.model.Astronomy;

/**
 * DAO для работы с Astronomy
 */
public interface AstronomyDao {

    /**
     * Сохранить информацию о текущих астрономических условиях в базу данных
     *
     * @param astronomy информация о текущих астрономических условиях
     */
    void save(Astronomy astronomy);
}
