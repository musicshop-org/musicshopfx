package sharedrmi.application;


import org.apache.activemq.ActiveMQConnectionFactory;
import sharedrmi.application.api.MessageConsumerService;
import sharedrmi.application.dto.MessageDTO;
import sharedrmi.application.exceptions.NoMessagesFoundException;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Override
    public List<MessageDTO> getMessagesFromAllSubscribedTopics() throws NoMessagesFoundException {

        // TODO: implement method + throw exception

        return Collections.emptyList();
    }

    @Override
    public List<MessageDTO> getMessagesFromSubscribedTopic(String topicName) throws NoMessagesFoundException, IOException, JMSException, JMSException {

        // TODO: implement method + throw exception

        Connection connection = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input consumer name:");
        String consumerName = reader.readLine();

        Session session = null;
        try {
            // Producer
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.setClientID(consumerName);
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("system");

            MessageConsumer consumer = session.createDurableSubscriber(topic,consumerName);

            connection.start();

            Message message;
            while(true) {
                message = consumer.receive(1000);
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                }
            }

        } finally {
            assert session != null;
            session.close();
            if (connection != null) {
                connection.close();
            }
        }

        //return Collections.emptyList();
    }

}
