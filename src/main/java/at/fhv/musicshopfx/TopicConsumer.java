package at.fhv.musicshopfx;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import sharedrmi.application.exceptions.UserNotFoundException;
import sharedrmi.communication.rmi.RMIController;


import java.rmi.RemoteException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TopicConsumer implements MessageListener
{
    // Connection Factory for connection to the ActiveMQ server
    private final ActiveMQConnectionFactory connectionFactory;

    // Parameters
    private final String topicToConsume;
    private final String clientid;
    private Connection connection;
    private Session session;
    private RMIController rmiController = SessionManager.getInstance().getRMIController();

    public TopicConsumer(String topicToConsume, String clientId) throws RemoteException, NotLoggedInException, JMSException {
        this.connectionFactory = new ActiveMQConnectionFactory("tcp://10.0.40.162:61616");
        this.topicToConsume = topicToConsume;
        this.clientid = clientId+topicToConsume;
        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(this.clientid);
        connection.start();

        // Create a Session
        session = connection.createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);

        // Create a Topic. If the Topic already exists, it will be returned.
        Destination destination = session.createTopic(this.topicToConsume);

        // Create Durable Subscriber
        Topic topic = (Topic) destination;
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "Message Subscription");

        // Use MessageListener in order to consume messages (including past messages)
        // non-blocking (implement onMessage())
        topicSubscriber.setMessageListener(this);

    }

//    @Override
//    public void run()
//    {
//        try
//        {
//            // Create a Connection
//            Connection connection = connectionFactory.createConnection();
//            connection.setClientID(this.clientid);
//            connection.start();
//
//            // Create a Session
//            Session session = connection.createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
//
//            // Create a Topic. If the Topic already exists, it will be returned.
//            Destination destination = session.createTopic(this.topicToConsume);
//
//            // Create Durable Subscriber
//            Topic topic = (Topic) destination;
//            TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "Message Subscription");
//
//            // Use MessageListener in order to consume messages (including past messages)
//            // non-blocking (implement onMessage())
//            topicSubscriber.setMessageListener(this);
//
//            while(interrupted == false){
//
//            }
//            System.out.println("interrupted");
//            session.close();
//            connection.close();
//            // Use TopicSubscriber and consume incoming messages (including past messages) - blocking
//            // while (true)
//            // {
//            //     Message message = topicSubscriber.receive();
//            //     TextMessage textMessage = (TextMessage) message;
//            //     // message.acknowledge();
//            //     System.out.println("JMS Message received successfully: " + textMessage.getText());
//            // }
//
//            // Create MessageConsumer and consume incoming messages (no past messages) - blocking
//            // while (true)
//            // {
//            //     MessageConsumer messageConsumer = session.createConsumer(destination);
//            //     Message message = messageConsumer.receive();
//            //     TextMessage textMessage = (TextMessage) message;
//            //     message.acknowledge();
//            //     System.out.println("JMS Message received successfully: " + textMessage.getText());
//            // }
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }

    public void close() throws JMSException {
        session.close();
        connection.close();
    }

    public static void main(String[] args) throws RemoteException, NotLoggedInException {

//        // Create the Connection Factory
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
//
//        // Create a Consumer
//        Thread topicConsumerThread = new Thread(new TopicConsumer("systme", "essiga"));
//        topicConsumerThread.start();
    }

    @Override
    public void onMessage(Message message)
    {
        MessageConsumerService messageConsumerService = null;
        try {
            messageConsumerService = MessageConsumerServiceImpl.getInstance();
        } catch (RemoteException | NotLoggedInException | JMSException e) {
            e.printStackTrace();
        }
        System.out.println("MESSAGE RECEIVED");
        try {
            LocalDateTime messageTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getJMSTimestamp()), TimeZone.getDefault().toZoneId());
            LocalDateTime lastViewedTime = rmiController.getLastViewedForUser(SessionManager.getLoggedInUsername());
            if(messageTime.isAfter(lastViewedTime)){
                SessionManager.setNewMessages(true);
            }
            messageConsumerService.addMessage(message, topicToConsume);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

//        try {
//            message.acknowledge();
//            System.out.println("CONTENT: " + textmessage.getText());
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
    }
}