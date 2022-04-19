package sharedrmi.application;

import sharedrmi.application.api.MessageConsumerService;
import sharedrmi.application.dto.MessageDTO;
import sharedrmi.application.exceptions.NoMessagesFoundException;

import java.util.Collections;
import java.util.List;

public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Override
    public List<MessageDTO> getMessagesFromAllSubscribedTopics() throws NoMessagesFoundException {

        // TODO: implement method + throw exception

        return Collections.emptyList();
    }

    @Override
    public List<MessageDTO> getMessagesFromSubscribedTopic(String topic) throws NoMessagesFoundException {

        // TODO: implement method + throw exception

        return Collections.emptyList();
    }

}
