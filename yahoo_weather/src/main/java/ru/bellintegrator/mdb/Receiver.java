package ru.bellintegrator.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.exception.WeatherException;
import ru.bellintegrator.service.YahooService;
import ru.bellintegrator.view.City;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;

/**
 * Получатель сообщения из очереди от модуля admin_api
 */
@MessageDriven(name = "Receiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/yahoo"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class Receiver implements MessageListener {

    private final Logger log = LoggerFactory.getLogger(Receiver.class);

    @Inject
    private YahooService yahooService;

    /**
     * Получить сообщение
     *
     * @param message пришедшее сообщение
     */
    @Override
    public void onMessage(Message message) {
        if(message == null){
            throw new WeatherException("Message can not be null");
        }
        try {
            if(!message.isBodyAssignableTo(City.class)) {
                throw new WeatherException("Message must be of type City");
            }
            City city = message.getBody(City.class);
            if (city == null) {
                throw new WeatherException("City can not be null");
            }
            log.info("City message has been received");
            yahooService.getWeather(city.getName(), city.getRegion());

        } catch (JMSException ex) {
            throw new RuntimeException("Error processing JMS message", ex);
        }
    }
}
