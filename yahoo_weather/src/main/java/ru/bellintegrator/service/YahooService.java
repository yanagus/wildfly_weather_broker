package ru.bellintegrator.service;

import ru.bellintegrator.view.WeatherInfoView;

/**
 * Сервис получения погоды с weather.yahoo.com
 */
public interface YahooService {

    /**
     * Получить данные о погоде с сервиса Yahoo
     * @param city город
     * @param region регион
     */
    WeatherInfoView getWeather(String city, String region);

}
