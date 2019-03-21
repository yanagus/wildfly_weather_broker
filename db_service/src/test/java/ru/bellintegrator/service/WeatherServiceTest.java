package ru.bellintegrator.service;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.MockType;
import org.junit.Rule;
import org.junit.Test;
import ru.bellintegrator.dao.WeatherDao;
import ru.bellintegrator.mapper.MapperFacade;
import ru.bellintegrator.view.AstronomyView;
import ru.bellintegrator.view.AtmosphereView;
import ru.bellintegrator.view.ConditionView;
import ru.bellintegrator.view.CurrentObservationView;
import ru.bellintegrator.view.ForecastView;
import ru.bellintegrator.view.LocationView;
import ru.bellintegrator.view.WindView;
import ru.bellintegrator.view.WeatherInfoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;

/**
 * Тест класса сервиса WeatherService
 */
public class WeatherServiceTest {

    @Rule
    public EasyMockRule em = new EasyMockRule(this);

    @Mock
    private WeatherServiceImpl service;

    @Mock
    private WeatherDao weatherDao;

    @Mock(MockType.NICE)
    private MapperFacade mapperFacade;

    public WeatherServiceTest() {
    }

    /**
     * Тест сохранения и получения данных о погоде
     */
    @Test
    public void testSaveWeather() {

        LocationView location = new LocationView(2123272, "Saratov", " Saratov Oblast",
                "Russia", 51.54332, 45.959949, "Europe/Volgograd");

        CurrentObservationView currentObservation = new CurrentObservationView(1552996800, null);
        WindView wind = new WindView(-25, 360, 13.0f, currentObservation);
        AtmosphereView atmosphere = new AtmosphereView(80, 16.1f, 1016.0f, 0, currentObservation);
        AstronomyView astronomy = new AstronomyView("7:44 am", "4:34 pm", currentObservation);
        ConditionView condition = new ConditionView("Mostly Clear", (short) 33, 17, currentObservation);
        currentObservation.setAstronomy(astronomy);
        currentObservation.setAtmosphere(atmosphere);
        currentObservation.setWind(wind);
        currentObservation.setCondition(condition);

        ForecastView forecast1 = new ForecastView("Tue", 1546934400, (byte) -19, (byte) -6, "Snow Showers", (short) 14);
        ForecastView forecast2 = new ForecastView("Wed", 1547020800, (byte) -21, (byte) -17, "Partly Cloudy", (short) 30);
        ForecastView forecast3 = new ForecastView("Thu", 1547107200, (byte) -21, (byte) -8, "Mostly Cloudy", (short) 28);
        ForecastView forecast4 = new ForecastView("Fri", 1547193600, (byte) -8, (byte) -6, "Snow", (short) 16);
        ForecastView forecast5 = new ForecastView("Sat", 1547280000, (byte) -7, (byte) -5, "Mostly Cloudy", (short) 28);
        List<ForecastView> forecasts = new ArrayList<>();
        Collections.addAll(forecasts, forecast1, forecast2, forecast3, forecast4, forecast5);

        WeatherInfoView weatherInfo = new WeatherInfoView(location, currentObservation, forecasts);

        service.saveWeather(weatherInfo);
        expect(service.getWeatherFromDB("Saratov")).andStubReturn(weatherInfo);
    }
}
