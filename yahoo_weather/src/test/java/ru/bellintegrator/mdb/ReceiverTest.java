package ru.bellintegrator.mdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.bellintegrator.service.YahooService;
import ru.bellintegrator.view.City;

import javax.jms.JMSException;
import javax.jms.Message;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тест класса-получателя сообщений из очереди от модуля admin_api
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceiverTest {

    @Mock
    private YahooService yahooService;

    @InjectMocks
    private Receiver receiver;

    /**
     * Тест получателя при корректном сообщении
     *
     * @throws JMSException исключение, выбрасываемое методом isBodyAssignableTo()
     */
    @Test
    public void receiveTest() throws JMSException {

        Message message = mock(Message.class);
        City city = new City("Saratov", "Saratov Oblast");

        when(message.isBodyAssignableTo(City.class)).thenReturn(true);
        when(message.getBody(City.class)).thenReturn(city);

        receiver.onMessage(message);

        verify(yahooService, atLeast(1)).getWeather(city.getName(), city.getRegion());
    }

    /**
     * Тест получателя при некорректном сообщении
     *
     * @throws JMSException исключение, выбрасываемое методом isBodyAssignableTo()
     */
    @Test
    public void incorrectTypeMessageTest() throws JMSException {
        Message message = mock(Message.class);
        when(message.isBodyAssignableTo(City.class)).thenReturn(false);

        try {
            receiver.onMessage(message);
        }catch (RuntimeException e){
            assertEquals(e.getMessage(), "Message must be of type City");
        }
    }

    /**
     * Тест получателя при message = null
     */
    @Test
    public void nullMessageTest(){
        Message message = null;

        try {
            receiver.onMessage(message);
        }catch (RuntimeException e){
            assertEquals(e.getMessage(), "Message can not be null");
        }
    }
}
