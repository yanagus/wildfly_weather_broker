package ru.bellintegrator.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.service.Sender;
import ru.bellintegrator.view.City;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AdminServletTest {

    private String attribute = "errorMessage";
    private String forwargUrl = "/index.jsp";

    @Mock
    private Sender sender;

    @InjectMocks
    private AdminServlet servlet;

    /**
     * Проверка при пустых значениях
     *
     * @throws ServletException иссключение метода doPost
     * @throws IOException иссключение метода doPost
     */
    @Test
    public void CityWithNullTest() throws ServletException, IOException {

        City city = new City(null, null);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("name")).thenReturn(city.getName());
        when(request.getParameter("region")).thenReturn(city.getRegion());
        when(request.getRequestDispatcher(forwargUrl)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verifyZeroInteractions(sender);
        verify(request, atLeast(1)).setAttribute(attribute, "City or Region can not be blank");
        verify(request, atLeast(1)).getRequestDispatcher(forwargUrl);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    /**
     * Проверка при введённых пробелах
     *
     * @throws ServletException иссключение метода doPost
     * @throws IOException иссключение метода doPost
     */
    @Test
    public void cityWithSpacesTest() throws ServletException, IOException {

        City city = new City("  ", " ");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("name")).thenReturn(city.getName());
        when(request.getParameter("region")).thenReturn(city.getRegion());
        when(request.getRequestDispatcher(forwargUrl)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verifyZeroInteractions(sender);
        verify(request, atLeast(1)).setAttribute(attribute, "City or Region can not be blank");
        verify(request, atLeast(1)).getRequestDispatcher(forwargUrl);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}
