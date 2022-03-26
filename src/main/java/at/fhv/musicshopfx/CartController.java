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
    private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML
    private TableColumn<AlbumDTO, String> artistCol;
    @FXML
    private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<AlbumDTO, String> quantityCol;
    @FXML
    private TableColumn<AlbumDTO, String> priceCol;
    @FXML
    private TableColumn<AlbumDTO, Image> xCol;
    @FXML
    private Label totalLabel;

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


        List<CartLineItem> cartLineItemList = new ArrayList<>();
        String imagePath = "src/main/resources/at/fhv/musicshopfx/images/cross.png";

        for (AlbumDTO singleDTO : albumDTOs)
        {
            cartLineItemList.add(new CartLineItem(singleDTO.getTitle(),
                                                  "Artist",
                                                  singleDTO.getMediumType(),
                                                  1,
                                                  singleDTO.getPrice(),
                                                  getImageView(imagePath, 20, 20)
            ));
        }

        ObservableList<CartLineItem> obsDTOs = FXCollections.observableArrayList(cartLineItemList);

        xCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("medium"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        cartView.setItems(obsDTOs);
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

        if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
            CartLineItem cartLineItem = cartView.getSelectionModel().getSelectedItem();
            int idx = cartView.getSelectionModel().getSelectedIndex();

            System.out.println(cartLineItem.getTitle() + " " + idx);
        }
    }

    @FXML
    protected void buyButtonClicked() {
        System.out.println("Buy Button clicked!");
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
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