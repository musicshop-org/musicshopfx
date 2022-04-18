package sharedrmi.application;

import sharedrmi.application.api.MessageConsumerService;
import sharedrmi.application.dto.MessageDTO;

import java.util.List;

public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Override
    public List<MessageDTO> getMessagesFromAllSubscribedTopics() {
        return null;
    }

    @Override
    public List<MessageDTO> getMessagesFromSubscribedTopic(String topic) {
        return null;
    }

}
