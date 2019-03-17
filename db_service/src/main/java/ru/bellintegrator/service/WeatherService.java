package ru.bellintegrator.service;

import ru.bellintegrator.view.WeatherInfoView;

/**
 * Сервис для обработки данных о погоде из БД
 */
public interface WeatherService {

    /**
     * Получить данные о погоде из БД
     *
     * @param city название города
     * @return данные о погоде
     */
    WeatherInfoView getWeatherFromDB(String city);

    /**
     * Сохранить данные о погоде в БД
     *
     * @param weatherInfoView данные о погоде
     */
    void saveWeather(WeatherInfoView weatherInfoView);
}
