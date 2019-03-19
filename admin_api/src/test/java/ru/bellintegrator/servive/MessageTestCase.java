//package ru.bellintegrator.servive;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.container.test.api.RunAsClient;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.as.arquillian.api.ServerSetup;
//import org.jboss.as.arquillian.api.ServerSetupTask;
//import org.jboss.as.arquillian.container.ManagementClient;
//import org.jboss.as.controller.client.ModelControllerClient;
//import org.jboss.as.test.integration.common.jms.JMSOperations;
//import org.jboss.shrinkwrap.api.asset.FileAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.slf4j.Logger;
//import ru.bellintegrator.service.Sender;
//
//import javax.ejb.EJB;
//
//@RunWith(Arquillian.class)
//@ServerSetup(MessagingResourcesSetupTask.class)
//public class MessageTestCase {
//
//    private static final Logger logger = getLogger(MessageTestCase.class.getName());
//
//    @EJB
//    private Sender messageQueueSender;
//
//    static class MessagingResourcesSetupTask implements ServerSetupTask {
//
//        @Override
//        public void setup(ManagementClient managementClient, String containerId) throws Exception {
//            JMSOperations jmsOperations = getInstance(managementClient.getControllerClient());
//            jmsOperations.createJmsQueue(QUEUE_NAME, QUEUE_LOOKUP);
//            jmsOperations.createJmsTopic(TOPIC_NAME, TOPIC_LOOKUP);
//        }
//
//        @Override
//        public void tearDown(ManagementClient managementClient, String containerId) throws Exception {
//            JMSOperations jmsOperations = getInstance(managementClient.getControllerClient());
//            jmsOperations.removeJmsQueue(QUEUE_NAME);
//            jmsOperations.removeJmsTopic(TOPIC_NAME);
//        }
//    }
//
//    @Deployment
//    public static JavaArchive createEJBDeployment() {
//        final JavaArchive jar = create(JavaArchive.class, "message.jar");
//        jar.addPackage(MessageQueueSender.class.getPackage());
//        return jar;
//    }
//
//    @Test
//    public void testSendQueueMessage() throws JMSException {
//        logger.info("Start send message queue test");
//        messageQueueSender.sendMessage("hello!");
//        Message message = messageQueueReceiver.receiveMessage();
//        String body = message.getBody(String.class);
//        assertEquals("the message is received: ", "hello!", body);
//    }
//}
