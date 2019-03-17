package ru.bellintegrator.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Общие данные о погоде
 */
public class WeatherInfoView implements Serializable {

    private static final long serialVersionUID = 803745049486954915L;

    /**
     * Местоположение
     */
    private LocationView location;

    /**
     * Текущие данные о погоде
     */
    @JsonProperty("current_observation")
    private CurrentObservationView currentObservation;

    /**
     * Прогноз на 10 дней
     */
    private List<ForecastView> forecasts;

    public WeatherInfoView() {
    }

    public WeatherInfoView(LocationView location, CurrentObservationView currentObservation, List<ForecastView> forecasts) {
        this.location = location;
        this.currentObservation = currentObservation;
        this.forecasts = forecasts;
    }

    public LocationView getLocation() {
        return location;
    }

    public void setLocation(LocationView location) {
        this.location = location;
    }

    public CurrentObservationView getCurrentObservation() {
        return currentObservation;
    }

    public void setCurrentObservation(CurrentObservationView currentObservation) {
        this.currentObservation = currentObservation;
        this.currentObservation.setDate();
    }

    public List<ForecastView> getForecasts() {
        if (forecasts == null) {
            forecasts = new ArrayList<>();
        }
        return forecasts;
    }

    public void setForecasts(List<ForecastView> forecasts) {
        this.forecasts = forecasts;
        for (ForecastView forecast : this.forecasts) {
            forecast.setZonedDateTime();
        }
    }

    @Override
    public String toString() {
        return "WeatherInfoView{" +
                "location=" + location +
                ", currentObservation=" + currentObservation +
                ", forecasts=" + forecasts +
                '}';
    }
}
