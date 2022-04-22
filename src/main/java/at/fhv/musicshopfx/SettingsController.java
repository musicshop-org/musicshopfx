package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class SettingsController {

    @FXML
    private Button applyButton;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView logoutIconImage;
    @FXML
    private ImageView messageIconImage;
    @FXML
    private ImageView searchIconImage;
    @FXML
    private ImageView settingsIconImage;
    @FXML
    private TableView<TopicLine> subscriptionView;
    @FXML
    private TableColumn<TopicLine, CheckBox> subscribedCol;
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
        subscribedCol.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        data = obsTopicLines;
        subscriptionView.setItems(data);
        subscriptionView.getSelectionModel().clearSelection();

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

                    // only operator -> move message to pos 2
                    cartIconImage.setVisible(true);
                    cartIconImage.setImage(messageIconImage.getImage());
                    cartIconImage.setOnMousePressed(messageIconImage.getOnMousePressed());
                    cartIconImage.setOnMouseClicked(messageIconImage.getOnMouseClicked());
                    cartIconImage.setFitHeight(26);
                    cartIconImage.setFitWidth(26);

                    // only operator -> move settings to pos 3
                    settingsIconImage.setVisible(false);
                    invoiceIconImage.setVisible(true);
                    invoiceIconImage.setImage(settingsIconImage.getImage());
                    invoiceIconImage.setOnMousePressed(settingsIconImage.getOnMousePressed());
                    invoiceIconImage.setOnMouseClicked(settingsIconImage.getOnMouseClicked());
                } else {
                    // operator & salesperson
                    this.messageIconImage.setVisible(true);
                }
            }
        }

        // only salesperson -> move settings to pos 4
        if (!messageIconImage.isVisible() && settingsIconImage.isVisible()) {
            settingsIconImage.setVisible(false);
            messageIconImage.setVisible(true);
            messageIconImage.setImage(settingsIconImage.getImage());
            messageIconImage.setOnMousePressed(settingsIconImage.getOnMousePressed());
            messageIconImage.setOnMouseClicked(settingsIconImage.getOnMouseClicked());
            messageIconImage.setFitHeight(30);
            messageIconImage.setFitWidth(30);
        }
    }

    @FXML
    void searchSymbolClicked(MouseEvent e) throws IOException {
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
    protected void settingsSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToSettingsView(e);
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
    void applyButtonClicked(ActionEvent event) {
        System.out.println("APPLY CLICKED");
    }
}
