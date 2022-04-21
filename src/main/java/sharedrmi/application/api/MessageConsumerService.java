package sharedrmi.application.api;

import sharedrmi.application.dto.MessageDTO;
import sharedrmi.application.exceptions.NoMessagesFoundException;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.List;

public interface MessageConsumerService {

    List<MessageDTO> getMessagesFromAllSubscribedTopics() throws NoMessagesFoundException;

    List<MessageDTO> getMessagesFromSubscribedTopic(String topic) throws NoMessagesFoundException, IOException, JMSException;

}
