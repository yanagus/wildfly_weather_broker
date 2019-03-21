package ru.bellintegrator.servive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.service.Sender;
import ru.bellintegrator.view.City;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.atLeast;

/**
 * Тест класса-отправителя сообщений
 */
@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

    @Mock
    private JMSContext context;
    @Mock
    private Queue queue;

    @InjectMocks
    private Sender sender;

    /**
     * Проверка отправки сообщения
     */
    @Test
    public void sendTest(){

        City city = new City("Saratov", "Saratov Oblast");
        JMSProducer producer = mock(JMSProducer.class);

        when(context.createProducer()).thenReturn(producer);

        sender.sendMessage(city);

        verify(context, atLeast(1)).createProducer();
        verify(producer, atLeast(1)).send(queue, city);
    }
}
