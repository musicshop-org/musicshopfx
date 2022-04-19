package sharedrmi.application.api;

import sharedrmi.application.dto.MessageDTO;
import sharedrmi.application.exceptions.NoMessagesFoundException;

import java.util.List;

public interface MessageConsumerService {

    List<MessageDTO> getMessagesFromAllSubscribedTopics() throws NoMessagesFoundException;

    List<MessageDTO> getMessagesFromSubscribedTopic(String topic) throws NoMessagesFoundException;

}
