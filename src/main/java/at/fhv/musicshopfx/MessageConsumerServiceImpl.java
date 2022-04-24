package at.fhv.musicshopfx;


import sharedrmi.communication.rmi.RMIController;

import javax.jms.*;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageConsumerServiceImpl implements MessageConsumerService {

    private static MessageConsumerService instance;
    private List<Thread> topicConsumerThreads;
    private SessionManager sessionManager;
    private RMIController rmiController;
    private static Map<String, List<Message>> topicMessages;

    private MessageConsumerServiceImpl() throws NotLoggedInException, RemoteException {
        this.sessionManager = SessionManager.getInstance();

        rmiController = sessionManager.getRMIController();

        String username = sessionManager.getLoggedInUsername();
        List<String> topics = rmiController.getSubscribedTopicsForUser(username);

        for (String topic:topics) {
            Thread topicConsumerThread = new Thread(new TopicConsumer(topic, username));
            topicConsumerThreads.add(topicConsumerThread);
            topicConsumerThread.start();
        }

    }

    public static MessageConsumerService getInstance() throws RemoteException, NotLoggedInException {
        if(instance == null){
            instance = new MessageConsumerServiceImpl();
        }

        return instance;
    }

    @Override
    public Map<String, List<Message>> getMessagesFromAllSubscribedTopics(){
        return topicMessages;
    }

    public List<Message> getMessagesFromSubscribedTopic(String topic){
        return topicMessages.get(topic);
    }

    @Override
    public void addMessage(Message message, String topic){
        List<Message> messages = topicMessages.get(topic);
        messages.add(message);
    }

    @Override
    public void removeMessage(Message message, String topic){
        List<Message> messages = topicMessages.get(topic);
        messages.remove(message);
    }

}
