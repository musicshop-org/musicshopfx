package at.fhv.musicshopfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.*;
import sharedrmi.application.exceptions.AlbumNotFoundException;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.enums.PaymentMethod;
import sharedrmi.domain.valueobjects.InvoiceId;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @FXML
    private ToggleGroup paymentMethod;
    @FXML
    private Label checkoutErrorLabel;
    @FXML
    private ToggleGroup customerSettingsToggleGroup;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView messageIconImage;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();
    private RMIController rmiController;
    private List<Role> roles;
    private List<CartLineItemDTO> cartLineItemDTOs;


    public void setData(List<CartLineItemDTO> cartLineItemDTOs) throws IOException {
        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        customerSettingsToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> changed,
                                Toggle oldVal, Toggle newVal) {
                switch (((RadioButton) customerSettingsToggleGroup.getSelectedToggle()).getText()){
                    case "Anonymous Customer":
                        searchButton.setDisable(true);
                        customerSearchTextField.setDisable(true);
                        customerTableView.setDisable(true);
                        break;

                    case "Existing Customer":
                        searchButton.setDisable(false);
                        customerSearchTextField.setDisable(false);
                        customerTableView.setDisable(false);
                }
            }
        });

        this.cartLineItemDTOs = cartLineItemDTOs;

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
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
    }

    @FXML
    protected void messageSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMessageProducerView(e);
    }

    @FXML
    protected void customerSearchButtonClicked() {
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

    @FXML
    protected void checkoutButtonClicked(ActionEvent event) {

        List<AlbumDTO> albums = new ArrayList<>();
        for (CartLineItemDTO cartLineItem : cartLineItemDTOs) {
            try {
                AlbumDTO album = rmiController.findAlbumByAlbumTitleAndMedium(cartLineItem.getName(), cartLineItem.getMediumType());
                int inStock = album.getStock();
                int boughtQuantity = cartLineItem.getQuantity();

                if (boughtQuantity > inStock) {
                    checkoutErrorLabel.setText("not enough " + album.getTitle() + " available ... in stock: " + inStock + ", in cart: " + boughtQuantity);
                    return;
                }

                albums.add(album);

            } catch (RemoteException | AlbumNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < albums.size(); i++) {
            AlbumDTO album = albums.get(i);
            int boughtQuantity = cartLineItemDTOs.get(i).getQuantity();
            rmiController.decreaseStockOfAlbum(album.getTitle(), album.getMediumType(), boughtQuantity);
        }

        SessionManager.setLastAlbums(null);
        SessionManager.setLastSearch("");

        PaymentMethod selectedPaymentMethod = PaymentMethod.CASH;

        switch (((RadioButton) paymentMethod.getSelectedToggle()).getText()) {
            case "Cash":
                selectedPaymentMethod = PaymentMethod.CASH;
                break;

            case "Credit Card":
                selectedPaymentMethod = PaymentMethod.CREDIT_CARD;
                break;
        }

        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .invoiceId(new InvoiceId())
                .date(LocalDate.now())
                .paymentMethod(selectedPaymentMethod)
                .invoiceLineItems(InvoiceLineItemDTO.createFromCartLineItemDTOs(cartLineItemDTOs))
                .build();

        try {
            rmiController.createInvoice(invoiceDTO);
            rmiController.clearCart();

            sceneSwitcher.switchSceneToMusicSearchView(event);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
