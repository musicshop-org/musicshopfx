package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sharedrmi.application.api.ProductService;
import sharedrmi.application.dto.AlbumDTO;


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class MusicSearchController {

    @FXML
    private Button musicSearchButton;

    @FXML
    private TextField musicSearchTextField;

    @FXML
    private Button switchSceneButton;

    @FXML
    private TableView<AlbumDTO> musicSearchResultTableView;
    @FXML
    private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML
    private TableColumn<AlbumDTO, String> releaseDateCol;
    @FXML
    private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<AlbumDTO, String> priceCol;


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    protected void onMusicSearchButtonClick() {

        //TODO: only call Naming.lookup at startup and add error handling
        try {
            ProductService productService = (ProductService) Naming.lookup("rmi://localhost/ProductService");

            List<AlbumDTO> albums = productService.findAlbumsBySongTitle(musicSearchTextField.getText());

            ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            musicSearchResultTableView.setItems(albumDTO);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }


    }


    @FXML
    protected void musicSearchResultTableViewMouseClick(MouseEvent e) throws IOException {

        if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
            AlbumDTO albumDTO = musicSearchResultTableView.getSelectionModel().getSelectedItem();


//            FXMLLoader loader = new FXMLLoader(getClass().getResource("productOverview-view.fxml"));
//
//            root = loader.load();
//            MusicOverviewController musicOverviewController = loader.getController();
//
//            musicOverviewController.setData(albumDTO);


            switchScene("productOverview-view.fxml", e, albumDTO);
//            loadProductOverviewScene(musicSearchResultTableView.getSelectionModel().getSelectedItem());

        }
    }

    @FXML
    protected void cartSymbolMouseClicked(MouseEvent e) throws IOException {
        System.out.println("cart clicked!");

        switchScene("cart-view.fxml");
    }

    private void loadProductOverviewScene(AlbumDTO albumDTO) {


    }

    private void switchScene(String fxml, MouseEvent event, AlbumDTO albumDTO) throws IOException {
        //FXMLLoader loader = FXMLLoader.load(getClass().getResource(fxml));
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        MusicOverviewController musicOverviewController = loader.getController();

        musicOverviewController.setData(albumDTO);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchScene(String fxml) throws IOException {
        //FXMLLoader loader = FXMLLoader.load(getClass().getResource(fxml));
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        MusicOverviewController musicOverviewController = loader.getController();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void switchSceneButtonClick(ActionEvent event) throws IOException {


    }

}