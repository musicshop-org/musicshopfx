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
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.LineItemDTO;
import sharedrmi.domain.CartLineItem;
import sharedrmi.domain.enums.MediumType;
import sharedrmi.domain.valueobjects.AlbumId;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private Label totalLabel;

    private ObservableList<CartLineItem> data;
    private final int MINUS_COLUMN_POSITION = 2;
    private final int PLUS_COLUMN_POSITION = 4;
    private final int CROSS_COLUMN_POSITION = 6;

    private final String BASE_IMAGE_PATH = "src/main/resources/at/fhv/musicshopfx/images/";
    private final String MINUS_PATH = BASE_IMAGE_PATH + "minus.png";
    private final String PLUS_PATH = BASE_IMAGE_PATH + "plus.png";
    private final String CROSS_PATH = BASE_IMAGE_PATH + "cross.png";

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setData() throws IOException {

        totalLabel.setText("15 €");
        System.out.println("Data set!");

        // TODO: Implement after adding to Shopping Cart is implemented!
//        try {
//            ShoppingCartServiceFactory shoppingCartServiceFactory = (ShoppingCartServiceFactory) Naming.lookup("rmi://localhost/CartFactory");
//            ShoppingCartService shoppingCartService = shoppingCartServiceFactory.createShoppingCartService(UUID.randomUUID());
//            ShoppingCartDTO shoppingCart = shoppingCartService.displayCart();
//
//        } catch (NotBoundException | MalformedURLException | RemoteException e) {
//            e.printStackTrace();
//        }

        // test data
//        List<AlbumDTO> albumDTOs = new ArrayList<>();
//        AlbumDTO dto = new AlbumDTO("song1", BigDecimal.ONE, 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str", null);
//        AlbumDTO dto2 = new AlbumDTO("song2", BigDecimal.TEN, 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str2", null);
//        AlbumDTO dto3 = new AlbumDTO("song3", BigDecimal.valueOf(20), 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str3", null);
//        albumDTOs.add(dto);
//        albumDTOs.add(dto2);
//        albumDTOs.add(dto3);

        // test data
        List<LineItemDTO> lineItemDTOS = new ArrayList<>();
        LineItemDTO dto = new LineItemDTO(MediumType.VINYL, "Bad", 3, BigDecimal.valueOf(18.99));
        LineItemDTO dto2 = new LineItemDTO(MediumType.CD, "Bad", 2, BigDecimal.valueOf(15.99));
        LineItemDTO dto3 = new LineItemDTO(MediumType.CD, "24K Magic", 10, BigDecimal.valueOf(20.99));
        lineItemDTOS.add(dto);
        lineItemDTOS.add(dto2);
        lineItemDTOS.add(dto3);

        List<CartLineItem> cartLineItemList = new ArrayList<>();

        for (LineItemDTO lineItemDTO : lineItemDTOS)
        {
            cartLineItemList.add(new CartLineItem(lineItemDTO.getName(),
                                                  lineItemDTO.getMediumType(),
                                                  lineItemDTO.getQuantity(),
                                                  lineItemDTO.getPrice(),
                                                  getImageView(MINUS_PATH, 12, 12),
                                                  getImageView(PLUS_PATH, 12, 12),
                                                  getImageView(CROSS_PATH, 18, 18),
                                                  lineItemDTO
            ));
        }

        ObservableList<CartLineItem> obsDTOs = FXCollections.observableArrayList(cartLineItemList);

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
    }

    private ImageView getImageView(String imagePath, int height, int width) throws FileNotFoundException {
        FileInputStream inpStr = new FileInputStream(imagePath);
        Image image = new Image(inpStr);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        return imageView;
    }

    @FXML
    protected void cartLineItemEdited (MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            CartLineItem cartLineItem = cartView.getSelectionModel().getSelectedItem();

            // row index
            int selectedRowIdx = cartView.getSelectionModel().getSelectedIndex();

            // col index
            ObservableList<TablePosition> pos = cartView.getSelectionModel().getSelectedCells();

            int selectedColIdx = -1;

            for (TablePosition po : pos)
            {
                selectedColIdx = po.getColumn();
            }

            if (selectedColIdx == MINUS_COLUMN_POSITION){

                if (cartLineItem.getQuantity() == 1)
                {
                    data.remove(selectedRowIdx);
                    cartView.getSelectionModel().clearSelection();
                    System.out.println("row removed!");
                    return;
                }

                data.set(selectedRowIdx, new CartLineItem(cartLineItem.getName(),
                                                          cartLineItem.getMedium(),
                                                   cartLineItem.getQuantity() - 1,
                                                          cartLineItem.getPrice(),
                                                          cartLineItem.getMinus_image(),
                                                          cartLineItem.getPlus_image(),
                                                          cartLineItem.getX_image(),
                                                          cartLineItem.getLineItemDTO()
                                                   ));
                System.out.println("quantity decremented!");
            }

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
                System.out.println("quantity incremented!");
            }

            else if (selectedColIdx == CROSS_COLUMN_POSITION){
                data.remove(selectedRowIdx);
                System.out.println("row removed!");
            }

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