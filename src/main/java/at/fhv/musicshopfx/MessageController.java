package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;
import java.rmi.RemoteException;

public class MessageController {

    @FXML
    private Label messageTopic;
    @FXML
    private Label messageTitle;
    @FXML
    private Label messageText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button readMoreButton;

    private MessageConsumerService messageConsumerService = MessageConsumerServiceImpl.getInstance();
    private Message message;
    private String topic;
    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public MessageController() throws RemoteException, NotLoggedInException {
    }

    public void addMessage(Message message, String topic) throws IOException, JMSException {

        this.messageTopic.setText(topic);
        this.messageTitle.setText(message.getJMSCorrelationID());
        this.messageText.setText(((TextMessage) message).getText());
        this.message = message;
        this.topic = topic;

    }

    @FXML
    public void deleteButtonOnClick(ActionEvent e) throws IOException {
        messageConsumerService.removeMessage(message, topic);
        sceneSwitcher.switchSceneToMessageBoardView(e);

    }
}
