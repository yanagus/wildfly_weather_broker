package ru.bellintegrator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.service.WeatherService;
import ru.bellintegrator.view.WeatherInfoView;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для получения погоды из БД
 */
@RestController
public class WeatherController {

    private Logger log = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Получить данные из параметров запроса и отобразить пользователю прогноз погоды из БД в формате JSON
     * Пример запроса:
     * http://127.0.0.1:8080/weather_service-1.0-SNAPSHOT/forecast?city=saratov
     *
     * @param city город
     * @return данные о погоде
     */
    @RequestMapping(value = "/forecast", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public WeatherInfoView getWeather(@RequestParam(value="city", defaultValue = "saratov") String city) {
        WeatherInfoView weatherInfoView = weatherService.getWeatherFromDB(city);
        if (weatherInfoView == null) {
            throw new NotFoundException("Current weather data has not been found");
        }
        return weatherInfoView;
    }

    /**
     * Обработчик ошибок о том, что актуальные данные о погоде не найдены в БД
     *
     * @param ex ошибка класса NotFoundException
     * @return строку с сообщением об ошибке
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}
