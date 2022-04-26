package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sharedrmi.application.exceptions.UserNotFoundException;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageBoardController {

    @FXML
    private ComboBox<String> topicSelection;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private Label messageErrorLabel;
    @FXML
    private VBox messagesVbox;
    @FXML
    private ScrollPane messagesScrollPane;
    @FXML
    private ImageView messageBoardIconImage;

    private final String messageFxml = "message.fxml";

    private MessageConsumerService messageConsumerService = MessageConsumerServiceImpl.getInstance();
    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public MessageBoardController() throws RemoteException, NotLoggedInException, JMSException {
    }

    public void setData() {

        messagesVbox.maxWidthProperty().bind(messagesScrollPane.widthProperty());
        VBox.setVgrow(messagesVbox, Priority.ALWAYS);
        if(SessionManager.isNewMessageAvailable()){
            messageBoardIconImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/fhv/musicshopfx/images/envelopered.png"))));
        }

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
        SessionManager.setNewMessages(false);
        try {
            rmiController.changeLastViewed(SessionManager.getLoggedInUsername(), LocalDateTime.now());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    protected void allTopicsSelected() throws IOException {

        this.messagesVbox.getChildren().clear();

        try {
            Map<String, List<Message>> topicMessages = messageConsumerService.getMessagesFromAllSubscribedTopics();

            if(topicMessages.isEmpty()){
                messageErrorLabel.setTextFill(Paint.valueOf("red"));
                messageErrorLabel.setText("no messages found");
            }
            else{
                addMessagesToBoard(topicMessages);
            }


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void topicSelected(ActionEvent e) throws IOException {

        this.messagesVbox.getChildren().clear();

        if (topicSelection.getValue().equals("All Topics")) {

            allTopicsSelected();

        } else {

            try {
                List<Message> messages = messageConsumerService.getMessagesFromSubscribedTopic(topicSelection.getValue());
                if(messages.isEmpty()){
                    messageErrorLabel.setTextFill(Paint.valueOf("red"));
                    messageErrorLabel.setText("no messages found");
                }
                else{
                    addMessagesToBoard(messages, topicSelection.getValue());
                }

            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }


    protected void addMessagesToBoard(Map<String, List<Message>> topicMessages) throws IOException, JMSException {

        messageErrorLabel.setText("");

        for (Map.Entry<String, List<Message>> topicMessage:topicMessages.entrySet()) {




            for (Message message:topicMessage.getValue()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(messageFxml));
                Parent messageFXML = loader.load();
                //AnchorPane anchorPane = (AnchorPane) root.lookup("messageAnchorPane");


                MessageController messageController = loader.getController();
                messageController.addMessage(message, topicMessage.getKey());
                this.messagesVbox.getChildren().add(0, messageFXML);
//                AnchorPane.setTopAnchor(messageFXML, 0.0);
//                AnchorPane.setRightAnchor(messageFXML, 0.0);
//                AnchorPane.setLeftAnchor(messageFXML, 0.0);

            }
        }
    }
    protected void addMessagesToBoard(List<Message> messages, String topic) throws IOException, JMSException {

        messageErrorLabel.setText("");

        for (Message message:messages) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(messageFxml));
            Parent root = loader.load();
            //AnchorPane anchorPane = (AnchorPane) root.lookup("messageAnchorPane");


            MessageController messageController = loader.getController();
            messageController.addMessage(message, topic);
            this.messagesVbox.getChildren().add(0, root);
//            AnchorPane.setTopAnchor(root, 0.0);
//            AnchorPane.setRightAnchor(root, 0.0);
//            AnchorPane.setLeftAnchor(root, 0.0);
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
