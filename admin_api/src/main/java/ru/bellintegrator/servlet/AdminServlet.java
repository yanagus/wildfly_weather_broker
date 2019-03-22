package ru.bellintegrator.servlet;

import ru.bellintegrator.exception.WeatherException;
import ru.bellintegrator.service.Sender;
import ru.bellintegrator.view.City;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для обработки запроса на обновление погоды
 */
@WebServlet("/update")
public class AdminServlet extends HttpServlet {

    @Inject
    private Sender sender;

    /**
     * Отобразить страницу с формой
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Обработать данные формы
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        City city = new City(request.getParameter("name"), request.getParameter("region"));

        if (isCityEmpty(city)) {
            forwardException(request, response, "City or Region can not be blank");
            return;
        }
        sender.sendMessage(city);
        forwardSuccess(request, response, city);
    }

    /**
     * Обновить страницу, если возникла ошибка
     *
     * @param attribute сообщение об ошибке
     */
    private void forwardException(HttpServletRequest request, HttpServletResponse response, String attribute) throws ServletException, IOException {
        request.setAttribute("errorMessage", attribute);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Загрузить страницу ответа в случае успешного обновления данных
     *
     * @param city город
     */
    private void forwardSuccess(HttpServletRequest request, HttpServletResponse response, City city) throws ServletException, IOException {
        request.setAttribute("city", city);
        getServletContext().getRequestDispatcher("/city.jsp").forward(request, response);
    }

    /**
     * Проверка вводимых данных
     *
     * @param city город
     * @return true, если данные введены
     */
    private boolean isCityEmpty(City city) {
        return city.getName() == null || city.getName().trim().isEmpty()
                || city.getRegion() == null || city.getRegion().trim().isEmpty();
    }
}
