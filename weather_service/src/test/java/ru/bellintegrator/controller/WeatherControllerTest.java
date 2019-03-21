package ru.bellintegrator.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.service.WeatherService;
import ru.bellintegrator.view.ResponseMessage;

import static org.junit.Assert.assertEquals;

/**
 * Тест класса контроллера
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    /**
     * Тест отсутствия актуальных данных о погоде за последние 6 часов в БД
     */
    @Test
    public void noCurrentForecastTest(){
        String location = "saratov";
        ResponseMessage message = new ResponseMessage("Current weather data has not been found");

        try {
            weatherController.getWeather(location);
        } catch (RuntimeException e){
            assertEquals(e.getMessage(), "Current weather data has not been found");
        }
    }
}
