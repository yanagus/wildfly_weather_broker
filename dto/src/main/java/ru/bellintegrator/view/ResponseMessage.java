package ru.bellintegrator.view;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс для сервисных сообщений контроллера
 */
public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = 803745049486954911L;

    /**
     * Сообщение
     */
    String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage that = (ResponseMessage) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
