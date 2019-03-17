package ru.bellintegrator.controller;

/**
 * Ошибка о том, что такая сущность не найдена в базе данных
 */
public class NotFoundException extends RuntimeException {

    /**
     * Конструктор
     *
     * @param message сообщение об ошибке
     */
    public NotFoundException(String message) {
        super(message);
    }
}
