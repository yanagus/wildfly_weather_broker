package ru.bellintegrator.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.view.WeatherInfoView;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Отправитель сообщения с данными о погоде в очередь для модуля db_service
 */
@ApplicationScoped
public class DataSender {

    private final Logger log = LoggerFactory.getLogger(DataSender.class);

    @Resource(mappedName = "java:/jms/queue/dbQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    /**
     * Отправить сообщение с данными о погоде в очередь для модуля db_service
     *
     * @param weatherInfoView данные о погоде
     */
    public void sendMessage(WeatherInfoView weatherInfoView) {
        context.createProducer().send(queue, weatherInfoView);
        log.info("WeatherInfoView message has been sent");
    }
}
