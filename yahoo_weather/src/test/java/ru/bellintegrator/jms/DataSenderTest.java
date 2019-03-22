package ru.bellintegrator.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.view.AstronomyView;
import ru.bellintegrator.view.AtmosphereView;
import ru.bellintegrator.view.ConditionView;
import ru.bellintegrator.view.CurrentObservationView;
import ru.bellintegrator.view.ForecastView;
import ru.bellintegrator.view.LocationView;
import ru.bellintegrator.view.WindView;
import ru.bellintegrator.view.WeatherInfoView;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.atLeast;

/**
 * Тест класса-отправителя сообщений
 */
@RunWith(MockitoJUnitRunner.class)
public class DataSenderTest {

    @Mock
    private JMSContext context;
    @Mock
    private Queue queue;

    @InjectMocks
    private DataSender sender;

    /**
     * Проверка отправки сообщения
     */
    @Test
    public void sendTest(){

        WeatherInfoView weatherInfo = createWeatherInfoView();

        JMSProducer producer = mock(JMSProducer.class);
        when(context.createProducer()).thenReturn(producer);
        sender.sendMessage(weatherInfo);

        verify(context, atLeast(1)).createProducer();
        verify(producer, atLeast(1)).send(queue, weatherInfo);
    }

    /**
     * Создать экземпляр WeatherInfoView
     *
     * @return WeatherInfoView
     */
    private WeatherInfoView createWeatherInfoView() {
        LocationView location = new LocationView(2123272, "Saratov", " Saratov Oblast",
                "Russia", 51.54332, 45.959949, "Europe/Volgograd");

        CurrentObservationView currentObservation = new CurrentObservationView(1546992000, null);
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

         return new WeatherInfoView(location, currentObservation, forecasts);
    }

}
