package sharedrmi.application.api;

import sharedrmi.application.dto.MessageDTO;

import java.util.List;

public interface MessageConsumerService {

    List<MessageDTO> getMessagesFromAllSubscribedTopics();

    List<MessageDTO> getMessagesFromSubscribedTopic(String topic);

}
