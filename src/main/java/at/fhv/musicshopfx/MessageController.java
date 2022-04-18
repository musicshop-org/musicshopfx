package at.fhv.musicshopfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sharedrmi.application.dto.MessageDTO;

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

    public void addMessages(MessageDTO message) throws IOException {

        this.messageTopic.setText(message.getMessageTopic());
        this.messageTitle.setText(message.getMessageTitle());
        this.messageText.setText(message.getMessageText());

    }

}
