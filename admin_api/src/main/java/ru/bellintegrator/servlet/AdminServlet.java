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
import java.io.PrintWriter;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        City city = new City(request.getParameter("name"), request.getParameter("region"));
        if (city.getName() == null || city.getName().equals("")
                || city.getRegion() == null || city.getRegion().equals("")) {
            throw new WeatherException("City can not be blank");
        }
        try {
            sender.sendMessage(city);
        } catch (WeatherException ex) {
            throw new ServletException(ex.getMessage());
        }

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter writer = response.getWriter()){
            request.setAttribute("city", city);
            getServletContext().getRequestDispatcher("/city.jsp").forward(request, response);
        } catch (IOException | IllegalStateException ex) {
            throw new WeatherException(ex.getMessage(), ex);
        }
    }
}
