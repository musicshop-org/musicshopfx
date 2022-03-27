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
    private TableColumn<CartLineItem, String> albumTitleCol;
    @FXML
    private TableColumn<CartLineItem, String> artistCol;
    @FXML
    private TableColumn<CartLineItem, String> mediumTypeCol;
    @FXML
    private TableColumn<CartLineItem, String> quantityCol;
    @FXML
    private TableColumn<CartLineItem, String> priceCol;
    @FXML
    private TableColumn<CartLineItem, String> plusCol;
    @FXML
    private TableColumn<CartLineItem, String> minusCol;
    @FXML
    private TableColumn<CartLineItem, String> xCol;
    @FXML
    private Label totalLabel;

    private ObservableList<CartLineItem> data;

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
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        AlbumDTO dto = new AlbumDTO("song1", BigDecimal.ONE, 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str", null);
        AlbumDTO dto2 = new AlbumDTO("song2", BigDecimal.TEN, 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str2", null);
        AlbumDTO dto3 = new AlbumDTO("song3", BigDecimal.valueOf(20), 3, MediumType.VINYL, LocalDate.now(), new AlbumId(), "str3", null);
        albumDTOs.add(dto);
        albumDTOs.add(dto2);
        albumDTOs.add(dto3);


        final String minusImagePath = "src/main/resources/at/fhv/musicshopfx/images/minus.png";
        final String plusImagePath = "src/main/resources/at/fhv/musicshopfx/images/plus.png";
        final String crossImagePath = "src/main/resources/at/fhv/musicshopfx/images/cross.png";

        List<CartLineItem> cartLineItemList = new ArrayList<>();

        for (AlbumDTO singleDTO : albumDTOs)
        {
            cartLineItemList.add(new CartLineItem(singleDTO.getTitle(),
                                                  "Artist",
                                                  singleDTO.getMediumType(),
                                                  getImageView(minusImagePath, 15, 15),
                                                  1,
                                                  getImageView(plusImagePath, 15, 15),
                                                  singleDTO.getPrice(),
                                                  getImageView(crossImagePath, 20, 20)
            ));
        }

        ObservableList<CartLineItem> obsDTOs = FXCollections.observableArrayList(cartLineItemList);

        xCol.setCellValueFactory(new PropertyValueFactory<>("x_image"));
        plusCol.setCellValueFactory(new PropertyValueFactory<>("plus_image"));
        minusCol.setCellValueFactory(new PropertyValueFactory<>("minus_image"));
        albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("medium"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        data = obsDTOs;

        cartView.getSelectionModel().setCellSelectionEnabled(true);
        cartView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        cartView.setItems(data);
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
    protected void cartViewClicked(MouseEvent e) {
        if (e.isPrimaryButtonDown() && e.getClickCount() == 1) {
            CartLineItem cartLineItem = cartView.getSelectionModel().getSelectedItem();

            // row index
            int rowIdx = cartView.getSelectionModel().getSelectedIndex();

            // col index
            ObservableList<TablePosition> pos = cartView.getSelectionModel().getSelectedCells();

            int colIdx = -1;

            for (TablePosition po : pos)
            {
                colIdx = po.getColumn();
            }

            // 3 == minus-Symbol
            if (colIdx == 3){

                if (cartLineItem.getQuantity() == 1)
                {
                    data.remove(rowIdx);
                    System.out.println("row removed!");
                    return;
                }

                data.set(rowIdx, new CartLineItem(cartLineItem.getTitle(),
                        cartLineItem.getArtist(),
                        cartLineItem.getMedium(),
                        cartLineItem.getMinus_image(),
                        cartLineItem.getQuantity() - 1,
                        cartLineItem.getPlus_image(),
                        cartLineItem.getPrice(),
                        cartLineItem.getX_image()));
                System.out.println("quantity decremented!");
            }

            // 5 == plus-Symbol
            if (colIdx == 5){
                data.set(rowIdx, new CartLineItem(cartLineItem.getTitle(),
                        cartLineItem.getArtist(),
                        cartLineItem.getMedium(),
                        cartLineItem.getMinus_image(),
                        cartLineItem.getQuantity() + 1,
                        cartLineItem.getPlus_image(),
                        cartLineItem.getPrice(),
                        cartLineItem.getX_image()));
                System.out.println("quantity incremented!");
            }

            // 7 == X-Symbol
            if (colIdx == 7){
                data.remove(rowIdx);
                System.out.println("row removed!");
            }
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