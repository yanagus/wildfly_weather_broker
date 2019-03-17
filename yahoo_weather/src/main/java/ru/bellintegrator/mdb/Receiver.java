package ru.bellintegrator.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.service.YahooService;
import ru.bellintegrator.view.City;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;

/**
 * Получатель сообщения от модуля admin_api
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
        ObjectMessage objectMessage = null;
        try {
            if(message instanceof ObjectMessage) {
                objectMessage = (ObjectMessage) message;
                City city = objectMessage.getBody(City.class);
                log.info("City message has been received");
                yahooService.getWeather(city.getName().trim().toLowerCase().replaceAll(" ", ""),
                            city.getRegion().trim().toLowerCase().replaceAll(" ", ""));
            } else {
                log.warn("Message of wrong type: " + message.getClass().getName());
                throw new IllegalArgumentException("Message must be of type ObjectMessage");
            }
        } catch (JMSException ex) {
            throw new RuntimeException("Error processing JMS message", ex);
        }
    }
}
