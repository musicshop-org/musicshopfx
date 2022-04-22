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

public class SettingsController {

    @FXML
    private Button applyButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label roleDescLabel;
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
    private String user;
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

        this.user = this.rmiController.getUsername();
        this.usernameLabel.setText(this.user);
        this.roleLabel.setText(concatRoles(this.roles));

        if (this.roles.size() == 1)
            this.roleDescLabel.setText("Role");


        List<String> allTopics = this.rmiController.getAllTopics();
        List<String> subscribedTopics = this.rmiController.getSubscribedTopicsForUser(this.user);
        List<TopicLine> topicLinesforTableView = new ArrayList<>();

        for (String currentTopic : allTopics) {
            TopicLine topicLine = new TopicLine(currentTopic);

            if (subscribedTopics.contains(currentTopic)) {
                topicLine.getCheckbox().setSelected(true);
            }

            topicLinesforTableView.add(topicLine);
        }

        ObservableList<TopicLine> obsTopicLines = FXCollections.observableArrayList(topicLinesforTableView);

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

    private String concatRoles(List<Role> roles) {
        StringBuilder concatedRoles = new StringBuilder();

        for (int i = 0; i < roles.size(); i++) {
            concatedRoles.append(roles.get(i));

            if (i < roles.size()-1)
                concatedRoles.append(", ");
        }

        return concatedRoles.toString().toLowerCase();
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
    void applyButtonClicked(ActionEvent e) throws RemoteException {

        List<String> subscribedTopics = this.rmiController.getSubscribedTopicsForUser(this.user);
        List<String> topicsToSubscribe = new ArrayList<>();
        List<String> topicsToUnsubscribe = new ArrayList<>();

        for (TopicLine line : data)
        {
            String topic = line.getTopicName();
            boolean topicIsCurrentlySelected = line.getCheckbox().isSelected();

            if (!subscribedTopics.contains(topic) && topicIsCurrentlySelected) {
                System.out.println("subscribe " + topic);
                // subscribe
            } else if (subscribedTopics.contains(topic) && !topicIsCurrentlySelected) {
                System.out.println("unsubscribe " + topic);
                // unsubscribe
            } else {
                System.out.println("nothing " + topic);
                // do nothing
            }
        }
    }
}
