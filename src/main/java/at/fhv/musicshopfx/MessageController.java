package at.fhv.musicshopfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

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

    public void addMessage(Message message, String topic) throws IOException, JMSException {

        this.messageTopic.setText(topic);
        this.messageTitle.setText(message.getJMSCorrelationID());
        this.messageText.setText(((TextMessage) message).getText());

    }

}
