package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.TopicLine;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MessageProducerController {

    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView messageIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private TextField expirationTextField;
    @FXML
    private TextField messageTitleTextField;
    @FXML
    private TextArea messageTextField;
    @FXML
    private Label titleErrorLabel;
    @FXML
    private Label expirationErrorLabel;
    @FXML
    private Label messageErrorLabel;
    @FXML
    private Label topicErrorLabel;
    @FXML
    private TableView<TopicLine> topicView;
    @FXML
    private TableColumn<TopicLine, CheckBox> publishCol;
    @FXML
    private TableColumn<TopicLine, String> topicCol;


    private RMIController rmiController;
    private List<Role> roles;
    private ObservableList<TopicLine> data;
    private SceneSwitcher sceneSwitcher = new SceneSwitcher();


    public void setData() throws RemoteException {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            e.printStackTrace();
        }

        List<String> topics = this.rmiController.getAllTopics();
        List<TopicLine> topicLines = new ArrayList<>();

        for (String topic : topics) {
            topicLines.add(new TopicLine(topic));
        }

        ObservableList<TopicLine> obsTopicLines = FXCollections.observableArrayList(topicLines);

        topicCol.setCellValueFactory(new PropertyValueFactory<>("topicName"));
        publishCol.setCellValueFactory(new PropertyValueFactory<>("publishCheckbox"));

        data = obsTopicLines;
        topicView.setItems(data);
        topicView.getSelectionModel().clearSelection();

        for (Role role : this.roles)
        {
            if (role.equals(Role.SALESPERSON)) {
                this.cartIconImage.setVisible(true);
                this.invoiceIconImage.setVisible(true);
            }
        }

        for (Role role : this.roles)
        {
            if (role.equals(Role.OPERATOR)) {
                if (!cartIconImage.isVisible()) {
                    cartIconImage.setVisible(true);
                    cartIconImage.setImage(messageIconImage.getImage());
                    cartIconImage.setOnMousePressed(messageIconImage.getOnMousePressed());
                    cartIconImage.setOnMouseClicked(messageIconImage.getOnMouseClicked());
                    cartIconImage.setFitHeight(26);
                    cartIconImage.setFitWidth(26);
                } else {
                    this.messageIconImage.setVisible(true);
                }
            }
        }
    }


    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMusicSearchView(e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToCartView (e);
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
    }

    @FXML
    protected void messageSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMessageProducerView(e);
    }

    @FXML
    protected void logoutSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            try {
                SessionManager.logout();
            } catch (NotLoggedInException ex) {
                ex.printStackTrace();
                return;
            }

            sceneSwitcher.switchSceneToLoginView(e);
        }
    }

    @FXML
    protected void publishButtonClicked(ActionEvent e) throws IOException {
        String messageTitle = messageTitleTextField.getText();
        String messageText = messageTextField.getText();

        if (messageTitle.isBlank()) {
            titleErrorLabel.setText("must not be empty");
            return;
        } else {
            titleErrorLabel.setText("");
        }

        int expirationDays;

        try {
            expirationDays = Integer.parseInt(expirationTextField.getText());

            if (expirationDays < 0) {
                expirationErrorLabel.setText("no valid value");
                return;
            }

            expirationErrorLabel.setText("");

        } catch (NumberFormatException ex) {
            expirationErrorLabel.setText("no valid value");
            return;
        }

        if (messageText.isBlank()) {
            messageErrorLabel.setText("must not be empty");
            return;
        } else {
            messageErrorLabel.setText("");
        }

        List<String> topicsToPublishMessage = new ArrayList<>();

        for (TopicLine line : data)
        {
            if (line.getPublishCheckbox().isSelected()) {
                topicsToPublishMessage.add(line.getTopicName());
            }
        }

        if (topicsToPublishMessage.isEmpty()) {
            topicErrorLabel.setText("at least one topic must be selected");
            return;
        } else {
            topicErrorLabel.setText("");
        }

        this.rmiController.publish(topicsToPublishMessage, messageTitle, messageText, expirationDays);

        sceneSwitcher.switchSceneToMusicSearchView(e);
    }
}
