package at.fhv.musicshopfx;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sharedrmi.application.dto.AlbumDTO;

import java.io.IOException;

public class CartController {

    @FXML
    private TableView<AlbumDTO> cartView;
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
    private TableColumn<AlbumDTO, String> xCol;
    @FXML
    private Label totalLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setData() {
        totalLabel.setText("15 €");
        System.out.println("Data set!");
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