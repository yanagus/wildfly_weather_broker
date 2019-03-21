package ru.bellintegrator.service;

import ru.bellintegrator.view.WeatherInfoView;

/**
 * Копия интерфейса с методом, удаленно вызываемым через Hessian
 */
public interface WeatherService {

    /**
     * Получить данные о погоде из БД
     *
     * @param city название города
     * @return данные о погоде
     */
    WeatherInfoView getWeatherFromDB(String city);
}
