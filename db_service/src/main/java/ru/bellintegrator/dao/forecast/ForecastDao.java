package ru.bellintegrator.dao.forecast;

import ru.bellintegrator.model.Forecast;

/**
 * DAO для работы с Forecast
 */
public interface ForecastDao {

    /**
     * Сохранить прогноз погоды в базу данных
     *
     * @param forecast прогноз погоды
     */
    void save(Forecast forecast);
}
