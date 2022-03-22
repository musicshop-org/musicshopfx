package at.fhv.musicshopfx.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sharedrmi.application.api.ProductService;
import sharedrmi.application.dto.AlbumDTO;


import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button musicSearchButton;

    @FXML
    private TextField musicSearchTextField;

//    @FXML
//    private TableView musicSearchResultTableView;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML private TableView<AlbumDTO> musicSearchResultTableView;
    @FXML private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML private TableColumn<AlbumDTO, String> releaseDateCol;
    @FXML private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML private TableColumn<AlbumDTO, String> priceCol;


    @FXML
    protected void onMusicSearchButtonClick(){


        //TODO: only call Naming.lookup at startup and add error handling
        try {
            ProductService productService = (ProductService) Naming.lookup("rmi://localhost/ProductService");

            List<AlbumDTO> albums = productService.findAlbumsByTitle(musicSearchTextField.getText());

            ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            musicSearchResultTableView.setItems(albumDTO);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}