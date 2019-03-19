package ru.bellintegrator.service;

/**
 * Сервис получения погоды с weather.yahoo.com
 */
public interface YahooService {

    /**
     * Получить данные о погоде с сервиса Yahoo
     * @param city город
     * @param region регион
     */
    void getWeather(String city, String region);

}
