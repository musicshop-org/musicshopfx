package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.SongDTO;

import java.util.List;
import java.util.Set;

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


    public void setData(AlbumDTO albumDTO){
        albumTitleTextField.setText(albumDTO.getTitle());
        mediumTypeTextField.setText(albumDTO.getMediumType().toString());
        releaseDateTextField.setText(albumDTO.getReleaseDate().toString());
        stockTextField.setText(Integer.toString(albumDTO.getStock()));
        priceTextField.setText(albumDTO.getPrice().toString());


        Set<SongDTO> songs = albumDTO.getSongs();

        ObservableList<SongDTO> songDTOs = FXCollections.observableArrayList(songs);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artists"));

        songsTableView.setItems(songDTOs);

    }


}
