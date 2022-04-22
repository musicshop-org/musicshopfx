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

        NavbarIconPositioner.positionIcons(this.roles,
                this.cartIconImage,
                this.invoiceIconImage,
                this.messageIconImage,
                this.settingsIconImage
        );
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
