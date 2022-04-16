package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class MessageProducerController {

    @FXML
    private ImageView searchIconImage;
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
    private TableView<TopicLine> topicView;
    @FXML
    private TableColumn<TopicLine, CheckBox> publishCol;
    @FXML
    private TableColumn<TopicLine, String> topicCol;
    @FXML
    private Button publishButton;


    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

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
    protected void publishButtonClicked(ActionEvent e) {
        String messageTitle = messageTitleTextField.getText();
        String messageText = messageTextField.getText();

        if ("".equals(messageTitle)) {
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

        if ("".equals(messageText)) {
            messageErrorLabel.setText("must not be empty");
            return;
        } else {
            messageErrorLabel.setText("");
        }

        System.out.println(messageTitle);
        System.out.println(messageText);
        System.out.println(expirationDays);
    }
}
