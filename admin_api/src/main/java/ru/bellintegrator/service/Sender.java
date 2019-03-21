package ru.bellintegrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bellintegrator.view.City;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Отправитель сообщения с названием города и региона в очередь для модуля yahoo_service
 */
public class Sender {

    private final Logger log = LoggerFactory.getLogger(Sender.class);

    @Resource(mappedName = "java:/jms/queue/yahoo")
    private Queue queue;

    @Inject
    private JMSContext context;

    /**
     * Отправить сообщение в очередь для модуля yahoo_service
     *
     * @param city город
     */
    public void sendMessage(City city) {
        context.createProducer().send(queue, city);
        log.info("City message has been sent");
    }
}
