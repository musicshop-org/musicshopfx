package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class MessageBoardController {

    @FXML
    private ComboBox topicSelection;

    @FXML
    private VBox messagesPane;


    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();


            // TODO: add subscribed topics to ComboBox


        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void topicSelected(ActionEvent e) {

        // TODO: list messages from selected topic

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
    protected void messageBoardSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMessageBoardView(e);
        }
    }

}
