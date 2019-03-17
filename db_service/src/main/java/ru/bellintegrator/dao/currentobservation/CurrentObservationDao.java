package ru.bellintegrator.dao.currentobservation;

import ru.bellintegrator.model.CurrentObservation;

/**
 * DAO для работы с CurrentObservation
 */
public interface CurrentObservationDao {

    /**
     * Сохранить информацию о текущем обзоре погоды в базу данных
     *
     * @param currentObservation текущий обзор погоды
     */
    void save(CurrentObservation currentObservation);

    /**
     * Найти обзор погоды не старее 6 часов с настоящего момента в базе данных
     *
     * @param woeid идентификатор города (WOEID - Where On Earth IDentifier)
     * @return текущий обзор погод в базе данных
     */
    CurrentObservation findByParameters(Integer woeid);
}
