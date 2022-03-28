package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sharedrmi.application.api.ShoppingCartService;
import sharedrmi.application.api.ShoppingCartServiceFactory;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.LineItemDTO;
import sharedrmi.application.dto.ShoppingCartDTO;
import sharedrmi.domain.CartLineItem;
import sharedrmi.domain.enums.MediumType;
import sharedrmi.domain.valueobjects.AlbumId;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CartController {

    @FXML
    private TableView<CartLineItem> cartView;
    @FXML
    private TableColumn<CartLineItem, String> productCol;
    @FXML
    private TableColumn<CartLineItem, String> mediumTypeCol;
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

    private ObservableList<CartLineItem> data;
    private final int MINUS_COLUMN_POSITION = 2;
    private final int PLUS_COLUMN_POSITION = 4;
    private final int CROSS_COLUMN_POSITION = 6;

    private final String BASE_IMAGE_PATH = "src/main/resources/at/fhv/musicshopfx/images/";
    private final String MINUS_PATH = BASE_IMAGE_PATH + "minus.png";
    private final String PLUS_PATH = BASE_IMAGE_PATH + "plus.png";
    private final String CROSS_PATH = BASE_IMAGE_PATH + "cross.png";

    private final String CURRENCY = "€";
    // needs to be the same UUID as in the MusicOverviewController
    private final UUID exampleEmployeeUUID = UUID.fromString("bb76c5ef-0c59-41ca-997f-2ba398631c7a");
    private ShoppingCartService shoppingCartService;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setData() throws IOException {

        try {
            ShoppingCartServiceFactory shoppingCartServiceFactory = (ShoppingCartServiceFactory) Naming.lookup("rmi://localhost/CartFactory");
            shoppingCartService = shoppingCartServiceFactory.createShoppingCartService(exampleEmployeeUUID);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

        // translate List<LineItemDTO> to List<CartLineItem>
        List<CartLineItem> cartLineItems = new ArrayList<>();

        for (LineItemDTO lineItemDTO : shoppingCartService.getCart().getLineItems())
        {
            cartLineItems.add(new CartLineItem(lineItemDTO.getName(),
                                                  lineItemDTO.getMediumType(),
                                                  lineItemDTO.getQuantity(),
                                                  lineItemDTO.getPrice(),
                                                  getImageView(MINUS_PATH, 12, 12),
                                                  getImageView(PLUS_PATH, 12, 12),
                                                  getImageView(CROSS_PATH, 18, 18),
                                                  lineItemDTO
            ));
        }

        // prepare UI table
        ObservableList<CartLineItem> obsDTOs = FXCollections.observableArrayList(cartLineItems);

        productCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("medium"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        minusCol.setCellValueFactory(new PropertyValueFactory<>("minus_image"));
        plusCol.setCellValueFactory(new PropertyValueFactory<>("plus_image"));
        xCol.setCellValueFactory(new PropertyValueFactory<>("x_image"));

        data = obsDTOs;

        cartView.setItems(data);

        cartView.getSelectionModel().clearSelection();

        // calculate and set total price
        double totalPrice = calculateTotalPrice(data.iterator());
        DecimalFormat df = new DecimalFormat("#.00");
        totalPriceLabel.setText(df.format(totalPrice) + " " + CURRENCY);
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

    private double calculateTotalPrice(Iterator<CartLineItem> iter)
    {
        double totalPrice = 0;

        while (iter.hasNext())
        {
            CartLineItem cartLineItem = iter.next();
            double price = cartLineItem.getPrice().doubleValue();
            int quantity = cartLineItem.getQuantity();
            totalPrice += price * quantity;
        }

        return totalPrice;
    }

    @FXML
    protected void cartLineItemEdited (MouseEvent e) throws RemoteException {

        if (e.isPrimaryButtonDown()) {
            CartLineItem cartLineItem = cartView.getSelectionModel().getSelectedItem();
            LineItemDTO lineItemDTO = cartLineItem.getLineItemDTO();

            // get selected row-index
            int selectedRowIdx = cartView.getSelectionModel().getSelectedIndex();

            // get selected column-index
            ObservableList<TablePosition> pos = cartView.getSelectionModel().getSelectedCells();

            int selectedColIdx = -1;

            for (TablePosition po : pos)
            {
                selectedColIdx = po.getColumn();
            }

            // minus clicked
            if (selectedColIdx == MINUS_COLUMN_POSITION){
                if (cartLineItem.getQuantity() == 1) {
                    data.remove(selectedRowIdx);
                    shoppingCartService.removeProductFromCart(lineItemDTO);
                }
                else
                {
                    data.set(selectedRowIdx, new CartLineItem(cartLineItem.getName(),
                            cartLineItem.getMedium(),
                            cartLineItem.getQuantity() - 1,
                            cartLineItem.getPrice(),
                            cartLineItem.getMinus_image(),
                            cartLineItem.getPlus_image(),
                            cartLineItem.getX_image(),
                            cartLineItem.getLineItemDTO()
                    ));

                    shoppingCartService.changeQuantity(lineItemDTO, cartLineItem.getQuantity() - 1);
                }
            }

            // plus clicked
            else if (selectedColIdx == PLUS_COLUMN_POSITION){
                data.set(selectedRowIdx, new CartLineItem(cartLineItem.getName(),
                        cartLineItem.getMedium(),
                        cartLineItem.getQuantity() + 1,
                        cartLineItem.getPrice(),
                        cartLineItem.getMinus_image(),
                        cartLineItem.getPlus_image(),
                        cartLineItem.getX_image(),
                        cartLineItem.getLineItemDTO()
                ));

                shoppingCartService.changeQuantity(lineItemDTO, cartLineItem.getQuantity() + 1);
            }

            // x clicked
            else if (selectedColIdx == CROSS_COLUMN_POSITION){
                data.remove(selectedRowIdx);
                shoppingCartService.removeProductFromCart(lineItemDTO);
            }

            double totalPrice = calculateTotalPrice(data.iterator());
            DecimalFormat df = new DecimalFormat("#.00");
            totalPriceLabel.setText(df.format(totalPrice) + " " + CURRENCY);

            cartView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    protected void buyButtonClicked() {
        System.out.println("Buy Button clicked!");
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchSceneToCartView("cart-view.fxml", e);
    }

    private void switchScene(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchSceneToCartView(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        CartController cartController = loader.getController();
        cartController.setData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}