package ru.bellintegrator.exception;

/**
 * Ошибка сервиса
 */
public class WeatherException extends RuntimeException {

    /**
     * Конструктор ошибки сервиса
     */
    public WeatherException(String message) {
        super(message);
    }

    /**
     * Конструктор ошибки сервиса с указанием причины
     */
    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
