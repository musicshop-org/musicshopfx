package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.CartLineItemDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.CartLineItem;
import sharedrmi.domain.valueobjects.Role;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartController {

    @FXML
    private TableView<CartLineItem> cartView;
    @FXML
    private TableColumn<CartLineItem, String> productCol;
    @FXML
    private TableColumn<CartLineItem, String> mediumTypeCol;
    @FXML
    private TableColumn<CartLineItem, String> stockCol;
    @FXML
    private TableColumn<CartLineItem, String> quantityCol;
    @FXML
    private TableColumn<CartLineItem, String> priceCol;
    @FXML
    private TableColumn<CartLineItem, String> minusCol;
    @FXML
    private TableColumn<CartLineItem, String> plusCol;
    @FXML
    private TableColumn<CartLineItem, String> xCol;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button buyButton;
    @FXML
    private Button clearCartButton;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView messageIconImage;

    private ObservableList<CartLineItem> data;
    private List<CartLineItemDTO> cartLineItemDTOs;

    private final int MINUS_COLUMN_POSITION = 3;
    private final int PLUS_COLUMN_POSITION = 5;
    private final int CROSS_COLUMN_POSITION = 7;

    private final String BASE_IMAGE_PATH = "src/main/resources/at/fhv/musicshopfx/images/";
    private final String MINUS_PATH = BASE_IMAGE_PATH + "minus.png";
    private final String CROSS_PATH = BASE_IMAGE_PATH + "cross.png";

    private final String CURRENCY = "€";
    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() throws IOException {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // translate List<LineItemDTO> to List<CartLineItem>
        List<CartLineItem> cartLineItems = new ArrayList<>();

        cartLineItemDTOs = rmiController.getCart().getCartLineItems();
        for (CartLineItemDTO cartLineItemDTO : cartLineItemDTOs) {
            cartLineItems.add(new CartLineItem(cartLineItemDTO.getName(),
                    cartLineItemDTO.getMediumType(),
                    cartLineItemDTO.getStock(),
                    cartLineItemDTO.getQuantity(),
                    cartLineItemDTO.getPrice(),
                    getImageView(MINUS_PATH, 12, 12),
                    "+",
                    getImageView(CROSS_PATH, 18, 18), cartLineItemDTO
            ));
        }

        // prepare UI table
        ObservableList<CartLineItem> obsDTOs = FXCollections.observableArrayList(cartLineItems);

        productCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("medium"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        minusCol.setCellValueFactory(new PropertyValueFactory<>("minus_image"));
        plusCol.setCellValueFactory(new PropertyValueFactory<>("plus"));
        xCol.setCellValueFactory(new PropertyValueFactory<>("x_image"));

        data = obsDTOs;

        cartView.setItems(data);

        cartView.getSelectionModel().clearSelection();

        // calculate and set total price
        if (rmiController.getCart().getCartLineItems().size() == 0) {
            totalPriceLabel.setText("0 " + CURRENCY);
        } else {
            double totalPrice = calculateTotalPrice(data.iterator());
            DecimalFormat df = new DecimalFormat("#.00");
            totalPriceLabel.setText(df.format(totalPrice) + " " + CURRENCY);
        }

        determineButtonStates();

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

    // get ImageView for UI table
    private ImageView getImageView(String pathToImage, int height, int width) throws FileNotFoundException {
        FileInputStream inpStr = new FileInputStream(pathToImage);

        Image image = new Image(inpStr);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        return imageView;
    }

    private double calculateTotalPrice(Iterator<CartLineItem> iter) {
        double totalPrice = 0;

        while (iter.hasNext()) {
            CartLineItem cartLineItem = iter.next();
            double price = cartLineItem.getPrice().doubleValue();
            int quantity = cartLineItem.getQuantity();
            totalPrice += price * quantity;
        }

        return totalPrice;
    }

    @FXML
    protected void cartLineItemEdited(MouseEvent e) throws RemoteException {

        if (e.isPrimaryButtonDown() && !cartView.getItems().isEmpty()) {

            CartLineItem cartLineItem = cartView.getSelectionModel().getSelectedItem();
            CartLineItemDTO cartLineItemDTO = cartLineItem.getLineItemDTO();

            // get selected row-index
            int selectedRowIdx = cartView.getSelectionModel().getSelectedIndex();

            // get selected column-index
            ObservableList<TablePosition> pos = cartView.getSelectionModel().getSelectedCells();

            int selectedColIdx = -1;

            for (TablePosition po : pos) {
                selectedColIdx = po.getColumn();
            }

            // minus clicked
            if (selectedColIdx == MINUS_COLUMN_POSITION) {
                if (cartLineItem.getQuantity() == 1) {
                    data.remove(selectedRowIdx);
                    rmiController.removeProductFromCart(cartLineItemDTO);
                } else {
                    data.set(selectedRowIdx, new CartLineItem(cartLineItem.getName(),
                            cartLineItem.getMedium(),
                            cartLineItem.getStock(),
                            cartLineItem.getQuantity() - 1,
                            cartLineItem.getPrice(),
                            cartLineItem.getMinus_image(),
                            cartLineItem.getPlus(),
                            cartLineItem.getX_image(),
                            cartLineItem.getLineItemDTO()
                    ));

                    rmiController.changeQuantity(cartLineItemDTO, cartLineItem.getQuantity() - 1);
                }
            }

            // plus clicked
            else if (selectedColIdx == PLUS_COLUMN_POSITION) {

                if (cartLineItem.getQuantity() < cartLineItem.getStock()) {
                    data.set(selectedRowIdx, new CartLineItem(cartLineItem.getName(),
                            cartLineItem.getMedium(),
                            cartLineItem.getStock(),
                            cartLineItem.getQuantity() + 1,
                            cartLineItem.getPrice(),
                            cartLineItem.getMinus_image(),
                            cartLineItem.getPlus(),
                            cartLineItem.getX_image(),
                            cartLineItem.getLineItemDTO()
                    ));

                    rmiController.changeQuantity(cartLineItemDTO, cartLineItem.getQuantity() + 1);
                }
            }

            // x clicked
            else if (selectedColIdx == CROSS_COLUMN_POSITION) {
                data.remove(selectedRowIdx);
                rmiController.removeProductFromCart(cartLineItemDTO);
            }

            if (rmiController.getCart().getCartLineItems().size() == 0) {
                totalPriceLabel.setText("0 " + CURRENCY);
            } else {
                double totalPrice = calculateTotalPrice(data.iterator());
                DecimalFormat df = new DecimalFormat("#.00");
                totalPriceLabel.setText(df.format(totalPrice) + " " + CURRENCY);
            }

            // prevents table row selection highlighting
            cartView.getSelectionModel().clearSelection();
        }

        determineButtonStates();
    }

    @FXML
    protected void buyButtonClicked(ActionEvent event) {

        try {
            cartLineItemDTOs = rmiController.getCart().getCartLineItems();
            sceneSwitcher.switchSceneToCheckoutView(event, cartLineItemDTOs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clearCartButtonClicked(ActionEvent actionEvent) {

        try {
            rmiController.clearCart();
            cartView.getItems().clear();

            determineButtonStates();

        } catch (RemoteException e) {
            e.printStackTrace();
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

    private void determineButtonStates() {
        boolean isCartEmpty = cartView.getItems().isEmpty();

        buyButton.setDisable(isCartEmpty);
        clearCartButton.setDisable(isCartEmpty);
    }
}