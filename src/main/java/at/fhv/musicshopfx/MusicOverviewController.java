package at.fhv.musicshopfx;

import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import sharedrmi.application.api.ProductService;
import sharedrmi.application.api.ShoppingCartService;
import sharedrmi.application.api.ShoppingCartServiceFactory;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MusicOverviewController {
    @FXML
    private TextField albumTitleTextField;
    @FXML
    private TextField artistTextField;
    @FXML
    private TextField mediumTypeTextField;
    @FXML
    private TextField releaseDateTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TableView songsTableView;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn genreCol;
    @FXML
    private TableColumn artistCol;
    @FXML
    private Button addToCartButton;
    @FXML
    private TextField quantityTextField;

    private AlbumDTO currentAlbumDTO;
    private final UUID exampleEmployeeUUID = UUID.fromString("bb76c5ef-0c59-41ca-997f-2ba398631c7a");

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void setData(AlbumDTO albumDTO){
        currentAlbumDTO = albumDTO;
        Set<SongDTO> songs = albumDTO.getSongs();
        albumTitleTextField.setText(albumDTO.getTitle());
        mediumTypeTextField.setText(albumDTO.getMediumType().toString());
        releaseDateTextField.setText(albumDTO.getReleaseDate().toString());
        stockTextField.setText(Integer.toString(albumDTO.getStock()));
        priceTextField.setText(albumDTO.getPrice().toString());

        Iterator<SongDTO> iter = albumDTO.getSongs().iterator();
        List<ArtistDTO> artists = iter.next().getArtists();
        ArtistDTO artist = artists.get(0);
        artistTextField.setText(artist.getName());

        ObservableList<SongDTO> songDTOs = FXCollections.observableArrayList(songs);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artists"));

        songsTableView.setItems(songDTOs);
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

    @FXML
    private void addToCartButtonClicked(){

        try {
            ShoppingCartServiceFactory shoppingCartServiceFactory = (ShoppingCartServiceFactory) Naming.lookup("rmi://localhost/CartFactory");
            ShoppingCartService shoppingCartService = shoppingCartServiceFactory.createShoppingCartService(exampleEmployeeUUID);
            shoppingCartService.addProductToCart(currentAlbumDTO, Integer.parseInt(quantityTextField.getText()));

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
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
