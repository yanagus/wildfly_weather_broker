package ru.bellintegrator.service;

import com.caucho.hessian.server.HessianServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.dao.WeatherDao;
import ru.bellintegrator.mapper.MapperFacade;
import ru.bellintegrator.model.Astronomy;
import ru.bellintegrator.model.Atmosphere;
import ru.bellintegrator.model.Condition;
import ru.bellintegrator.model.CurrentObservation;
import ru.bellintegrator.model.Forecast;
import ru.bellintegrator.model.Location;
import ru.bellintegrator.model.Wind;
import ru.bellintegrator.view.AstronomyView;
import ru.bellintegrator.view.AtmosphereView;
import ru.bellintegrator.view.ConditionView;
import ru.bellintegrator.view.CurrentObservationView;
import ru.bellintegrator.view.ForecastView;
import ru.bellintegrator.view.LocationView;
import ru.bellintegrator.view.WindView;
import ru.bellintegrator.view.WeatherInfoView;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@ApplicationScoped
public class WeatherServiceImpl extends HessianServlet implements WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Inject
    private WeatherDao weatherDao;

    @Inject
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public WeatherInfoView getWeatherFromDB(String city) {
        Location location = weatherDao.findLocationByCity(city);
        if(location == null) {
            return null;
        }
        CurrentObservation currentObservation = weatherDao.findCurrentObservationByWoeidAndDate(location.getWoeid());
        if(currentObservation == null) {
            return null;
        }
        currentObservation.setLocation(null);
        WeatherInfoView weatherInfoView = new WeatherInfoView();
        LocationView locationView = convertLocationToLocationView(location);
        weatherInfoView.setLocation(locationView);
        weatherInfoView.setCurrentObservation(mapperFacade.map(currentObservation, CurrentObservationView.class));
        List<Forecast> forecasts = location.getForecasts();
        weatherInfoView.setForecasts(convertForecastToForecastView(forecasts));
        return weatherInfoView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveWeather(WeatherInfoView weatherInfoView) {
        Location locationFromDB = weatherDao.findLocationByWoeid(weatherInfoView.getLocation().getWoeid());
        if (locationFromDB != null) {
            saveCurrentObservation(weatherInfoView.getCurrentObservation(), locationFromDB);
            if (!locationFromDB.getForecasts().isEmpty()) {
                locationFromDB.getForecasts().clear();
            }
            saveForecasts(weatherInfoView.getForecasts(), locationFromDB);
            log.info("Added new weather data to location {}", locationFromDB.getCity());
        } else {
            Location location = mapperFacade.map(weatherInfoView.getLocation(), Location.class);
            weatherDao.saveLocation(location);
            saveCurrentObservation(weatherInfoView.getCurrentObservation(), location);
            saveForecasts(weatherInfoView.getForecasts(), location);
            log.info("Saved new location {} and weather data to a database", location.getCity());
        }
    }

    /**
     * Сохранить прогноз погоды на 10 дней
     *
     * @param forecasts прогноз погоды на 10 дней
     * @param location местоположение
     */
    private void saveForecasts(List<ForecastView> forecasts, Location location) {
        for (ForecastView forecastView : forecasts) {
            Forecast forecast = mapperFacade.map(forecastView, Forecast.class);
            forecast.setLocation(location);
            weatherDao.saveForecast(forecast);
        }
    }

    /**
     * Сохранить текущий обзор погоды
     *
     * @param currentObservationView текущий обзор погоды
     * @param location местоположение
     */
    private void saveCurrentObservation(CurrentObservationView currentObservationView, Location location) {
        if(currentObservationView.getAstronomy() != null && currentObservationView.getAtmosphere() != null &&
                currentObservationView.getCondition() != null && currentObservationView.getWind() != null) {
            CurrentObservation newCurrentObservation = new CurrentObservation(location, currentObservationView.getPubDate());
            weatherDao.saveCurrentObservation(newCurrentObservation);

            saveAstronomy(currentObservationView.getAstronomy(), newCurrentObservation);
            saveAtmosphere(currentObservationView.getAtmosphere(), newCurrentObservation);
            saveCondition(currentObservationView.getCondition(), newCurrentObservation);
            saveWind(currentObservationView.getWind(), newCurrentObservation);
        }
    }

    /**
     * Сохранить информацию о текущих астрономических условиях
     *
     * @param astronomyView информация о текущих астрономических условиях
     * @param currentObservation текущий обзор погоды
     */
    private void saveAstronomy(AstronomyView astronomyView, CurrentObservation currentObservation) {
        Astronomy astronomy = mapperFacade.map(astronomyView, Astronomy.class);
        astronomy.setCurrentObservation(currentObservation);
        weatherDao.saveAstronomy(astronomy);
    }

    /**
     * Сохранить информацию о текущем атмосферном давлении, влажности и видимости
     *
     * @param atmosphereView информация о текущем атмосферном давлении, влажности и видимости
     * @param currentObservation текущий обзор погоды
     */
    private void saveAtmosphere(AtmosphereView atmosphereView, CurrentObservation currentObservation) {
        Atmosphere atmosphere = mapperFacade.map(atmosphereView, Atmosphere.class);
        atmosphere.setCurrentObservation(currentObservation);
        weatherDao.saveAtmosphere(atmosphere);
    }

    /**
     * Сохранить информацию о текущем состоянии погоды
     *
     * @param conditionView текущее состояние погоды
     * @param currentObservation текущий обзор погоды
     */
    private void saveCondition(ConditionView conditionView, CurrentObservation currentObservation) {
        Condition condition = mapperFacade.map(conditionView, Condition.class);
        condition.setCurrentObservation(currentObservation);
        weatherDao.saveCondition(condition);
    }

    /**
     * Сохранить текущую информацию о ветре
     *
     * @param windView текущая информация о ветре
     * @param currentObservation текущий обзор погоды
     */
    private void saveWind(WindView windView, CurrentObservation currentObservation) {
        Wind wind = mapperFacade.map(windView, Wind.class);
        wind.setCurrentObservation(currentObservation);
        weatherDao.saveWind(wind);
    }

    /**
     * Преобразовать Location в LocationView без ленивой инициализации
     *
     * @param location местоположение
     * @return dto LocationView
     */
    private LocationView convertLocationToLocationView(Location location) {
        return new LocationView(location.getWoeid(), location.getCity(), location.getRegion(), location.getCountry(),
                location.getLatitude(), location.getLongitude(), location.getTimezone());
    }

    /**
     * Преобразовать List<Forecast> в List<ForecastView> без ленивой инициализации
     *
     * @param forecasts прогноз погоды на 10 дней
     * @return dto List<ForecastView>
     */
    private List<ForecastView> convertForecastToForecastView(List<Forecast> forecasts) {
        List<ForecastView> forecastViews = new ArrayList<>();
        for (Forecast forecast : forecasts) {
            ForecastView forecastView = new ForecastView(forecast.getDay(), forecast.getDate(), forecast.getLow(),
                    forecast.getHigh(), forecast.getText(), forecast.getCode());
            forecastViews.add(forecastView);
        }
        return forecastViews;
    }
}
