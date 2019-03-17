package ru.bellintegrator.servlet;

import ru.bellintegrator.exceptionHandler.WeatherException;
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
@SuppressWarnings("serial")
@WebServlet("/update")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private Sender sender;

    public AdminServlet() {
        super();
    }

    /**
     * Отобразить страницу с формой
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Обработать данные формы
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        City city = new City(request.getParameter("name"), request.getParameter("region"));
        if (city.getName() == null || city.getName().equals("") ||
                city.getRegion() == null || city.getRegion().equals("")) {
            throw new WeatherException("City can not be blank");
        }
        sender.sendMessage(city);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            request.setAttribute("city", city);
            getServletContext().getRequestDispatcher("/city.jsp").forward(request, response);
        } finally {
            writer.close();
        }
    }

}
