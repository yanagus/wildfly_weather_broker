package ru.bellintegrator.dao;

import ru.bellintegrator.model.Astronomy;
import ru.bellintegrator.model.Atmosphere;
import ru.bellintegrator.model.Condition;
import ru.bellintegrator.model.CurrentObservation;
import ru.bellintegrator.model.Forecast;
import ru.bellintegrator.model.Location;
import ru.bellintegrator.model.Wind;

/**
 * DAO для работы с Entity
 */
public interface WeatherDao {

    /**
     * Сохранить местоположение в базу данных
     *
     * @param location местоположение
     */
    void saveLocation(Location location);

    /**
     * Сохранить прогноз погоды в базу данных
     *
     * @param forecast прогноз погоды
     */
    void saveForecast(Forecast forecast);

    /**
     * Сохранить информацию о текущих астрономических условиях в базу данных
     *
     * @param astronomy информация о текущих астрономических условиях
     */
    void saveAstronomy(Astronomy astronomy);

    /**
     * Сохранить информацию о текущем атмосферном давлении, влажности и видимости в базу данных
     *
     * @param atmosphere информация о текущем атмосферном давлении, влажности и видимости
     */
    void saveAtmosphere(Atmosphere atmosphere);

    /**
     * Сохранить информацию о текущем состоянии погоды в базу данных
     *
     * @param condition текущее состояние погоды
     */
    void saveCondition(Condition condition);

    /**
     * Сохранить текущую информацию о ветре в базу данных
     *
     * @param wind текущая информация о ветре
     */
    void saveWind(Wind wind);

    /**
     * Сохранить информацию о текущем обзоре погоды в базу данных
     *
     * @param currentObservation текущий обзор погоды
     */
    void saveCurrentObservation(CurrentObservation currentObservation);

    /**
     * Найти обзор погоды не старее 6 часов с настоящего момента в базе данных
     *
     * @param woeid идентификатор города (WOEID - Where On Earth IDentifier)
     * @return текущий обзор погод в базе данных
     */
    CurrentObservation findCurrentObservationByWoeidAndDate(Integer woeid);

    /**
     * Найти местоположение по идентификатору города
     * @param woeid идентификатор города (WOEID - Where On Earth IDentifier)
     * @return местоположение
     */
    Location findLocationByWoeid(Integer woeid);

    /**
     * Найти местоположение по городу
     *
     * @param city город
     * @return местоположение
     */
    Location findLocationByCity(String city);
}
