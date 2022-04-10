package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.CustomerDTO;
import sharedrmi.communication.rmi.RMIController;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class CheckoutController {

    @FXML
    private RadioButton anonymousCustomerRadioButton;
    @FXML
    private RadioButton existingCustomerRadioButton;
    @FXML
    private RadioButton cashRadioButton;
    @FXML
    private RadioButton creditCardRadioButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private TableView customerTableView;
    @FXML
    private Button searchButton;
    @FXML
    private TextField customerSearchTextField;
    @FXML
    private TableColumn firstNameCol;
    @FXML
    private TableColumn familyNameCol;
    @FXML
    private TableColumn emailAddressCol;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();
    private RMIController rmiController;

    public void setData() throws IOException {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();

        } catch (NotLoggedInException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
            sceneSwitcher.switchSceneToCartView(e);
    }


    @FXML
    protected void customerSearchButtonClicked(){
        try {

            List<CustomerDTO> customers = rmiController.findCustomersByName(customerSearchTextField.getText());

            ObservableList<CustomerDTO> customerDTOs = FXCollections.observableArrayList(customers);

            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            familyNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            emailAddressCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            customerTableView.setItems(customerDTOs);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
