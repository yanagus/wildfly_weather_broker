//package ru.bellintegrator.service;
//import org.easymock.EasyMockRule;
//import org.easymock.Mock;
//import org.easymock.MockType;
//import org.easymock.TestSubject;
//import org.junit.Rule;
//import org.junit.Test;
//import ru.bellintegrator.dao.astronomy.AstronomyDao;
//import ru.bellintegrator.dao.atmosphere.AtmosphereDao;
//import ru.bellintegrator.dao.condition.ConditionDao;
//import ru.bellintegrator.dao.currentobservation.CurrentObservationDao;
//import ru.bellintegrator.dao.forecast.ForecastDao;
//import ru.bellintegrator.dao.location.LocationDao;
//import ru.bellintegrator.dao.wind.WindDao;
//import ru.bellintegrator.model.*;
//import ru.bellintegrator.model.mapper.MapperFacade;
//import ru.bellintegrator.view.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.replay;
//import static org.easymock.EasyMock.verify;
//
//public class TestService {
//
//    Location location = new Location(2123272, "Saratov", " Saratov Oblast",
//            "Russia", 51.54332, 45.959949, "Europe/Volgograd");
//
//    CurrentObservation currentObservation = new CurrentObservation(location, 1546992000);
//    Wind wind = new Wind(-25, 360, 13.0f, currentObservation);
//    Atmosphere atmosphere = new Atmosphere(80, 16.1f, 1016.0f, 0, currentObservation);
//    Astronomy astronomy = new Astronomy("7:44 am", "4:34 pm", currentObservation);
//    Condition condition = new Condition("Mostly Clear", (short) 33, 17, currentObservation);
//
//    @Rule
//    public EasyMockRule em = new EasyMockRule(this);
//
//    @Mock
//    private WeatherServiceImpl service;
//
//    @Mock(MockType.NICE)
//    private LocationDao locationDao;
//    @Mock(MockType.NICE)
//    private AtmosphereDao atmosphereDao;
//    @Mock(MockType.NICE)
//    private AstronomyDao astronomyDao;
//    @Mock(MockType.NICE)
//    private ConditionDao conditionDao;
//    @Mock(MockType.NICE)
//    private WindDao windDao;
//    @Mock(MockType.NICE)
//    private CurrentObservationDao currentObservationDao;
//    @Mock(MockType.NICE)
//    private ForecastDao forecastDao;
//    @Mock(MockType.NICE)
//    private MapperFacade mapperFacade;
//
//    @TestSubject
//    private WeatherServiceImpl WeatherService = new WeatherServiceImpl(locationDao, atmosphereDao, astronomyDao,
//            conditionDao, windDao, currentObservationDao, forecastDao, mapperFacade);
//
//    public TestService() {
//    }
//
//    @Test
//    public void testSaveWeather() {
//        LocationView location = new LocationView(2123272, "Saratov", " Saratov Oblast",
//                "Russia", 51.54332, 45.959949, "Europe/Volgograd");
//
//        CurrentObservationView currentObservation = new CurrentObservationView(1546992000, null);
//        WindView wind = new WindView(-25, 360, 13.0f, currentObservation);
//        AtmosphereView atmosphere = new AtmosphereView(80, 16.1f, 1016.0f, 0, currentObservation);
//        AstronomyView astronomy = new AstronomyView("7:44 am", "4:34 pm", currentObservation);
//        ConditionView condition = new ConditionView("Mostly Clear", (short) 33, 17, currentObservation);
//        currentObservation.setAstronomy(astronomy);
//        currentObservation.setAtmosphere(atmosphere);
//        currentObservation.setWind(wind);
//        currentObservation.setCondition(condition);
//
//        ForecastView forecast1 = new ForecastView("Tue", 1546934400, (byte) -19, (byte) -6, "Snow Showers", (short) 14);
//        ForecastView forecast2 = new ForecastView("Wed", 1547020800, (byte) -21, (byte) -17, "Partly Cloudy", (short) 30);
//        ForecastView forecast3 = new ForecastView("Thu", 1547107200, (byte) -21, (byte) -8, "Mostly Cloudy", (short) 28);
//        ForecastView forecast4 = new ForecastView("Fri", 1547193600, (byte) -8, (byte) -6, "Snow", (short) 16);
//        ForecastView forecast5 = new ForecastView("Sat", 1547280000, (byte) -7, (byte) -5, "Mostly Cloudy", (short) 28);
//        List<ForecastView> forecasts = new ArrayList<>();
//        Collections.addAll(forecasts, forecast1, forecast2, forecast3, forecast4, forecast5);
//
//        WeatherInfoView weatherInfo = new WeatherInfoView(location, currentObservation, forecasts);
//        service.saveWeather(weatherInfo);
//    }
//
//}
