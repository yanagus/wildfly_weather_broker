package ru.bellintegrator.dao.location;

import ru.bellintegrator.model.Location;

/**
 * DAO для работы с Location
 */
public interface LocationDao {

    /**
     * Сохранить местоположение в базу данных
     *
     * @param location местоположение
     */
    void save(Location location);

    /**
     * Найти местоположение по идентификатору города
     * @param woeid идентификатор города (WOEID - Where On Earth IDentifier)
     * @return местоположение
     */
    Location findByWoeid(Integer woeid);

    /**
     * Найти местоположение по городу
     *
     * @param city город
     * @return местоположение
     */
    Location findByCity(String city);
}
