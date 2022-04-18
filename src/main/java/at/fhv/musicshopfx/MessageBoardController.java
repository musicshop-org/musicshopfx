package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sharedrmi.application.MessageConsumerServiceImpl;
import sharedrmi.application.api.MessageConsumerService;
import sharedrmi.application.dto.MessageDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class MessageBoardController {

    @FXML
    private ComboBox<String> topicSelection;
    @FXML
    private Label messageErrorLabel;
    @FXML
    private VBox messagesPane;

    private final String messageFxml = "message.fxml";

    private MessageConsumerService messageConsumerService = new MessageConsumerServiceImpl();
    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

            topicSelection.getItems().add("All Topics");
            List<String> subscribedTopics = rmiController.getSubscribedTopicsForUser(SessionManager.getLoggedInUsername());
            for (String subscribedTopic : subscribedTopics) {
                topicSelection.getItems().add(subscribedTopic);
            }

            this.allTopicsSelected();

        } catch (NotLoggedInException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    protected void allTopicsSelected() throws IOException {

        this.messagesPane.getChildren().clear();

        List<MessageDTO> messages = messageConsumerService.getMessagesFromAllSubscribedTopics();
        this.addMessagesToBoard(messages);
    }

    @FXML
    protected void topicSelected(ActionEvent e) throws IOException {

        this.messagesPane.getChildren().clear();

        if (topicSelection.getValue().equals("All Topics")) {
            allTopicsSelected();
        } else {
            List<MessageDTO> messages = messageConsumerService.getMessagesFromSubscribedTopic(topicSelection.getValue());
            this.addMessagesToBoard(messages);
        }
    }

    protected void addMessagesToBoard(List<MessageDTO> messages) throws IOException {

        messageErrorLabel.setText("");

        if (messages.isEmpty()) {

            messageErrorLabel.setTextFill(Paint.valueOf("red"));
            messageErrorLabel.setText("no messages found");

        } else {

            for (MessageDTO message : messages) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(messageFxml));
                Parent root = loader.load();

                MessageController messageController = loader.getController();
                messageController.addMessages(message);

                this.messagesPane.getChildren().add(root);
            }
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMusicSearchView(e);
        }
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToCartView(e);
        }
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
        }
    }

    @FXML
    protected void messageSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMessageProducerView(e);
    }

    @FXML
    protected void messageBoardSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMessageBoardView(e);
        }
    }

}
