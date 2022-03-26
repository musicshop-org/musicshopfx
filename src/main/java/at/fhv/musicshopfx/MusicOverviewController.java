package at.fhv.musicshopfx;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;

import java.util.Iterator;
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

//        while (iter.hasNext())
//        {
//            List<ArtistDTO> artists = iter.next().getArtists();
//            for(ArtistDTO a : artists)
//            {
//                System.out.println(a.getName());
//            }
//        }

        ObservableList<SongDTO> songDTOs = FXCollections.observableArrayList(songs);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artists"));


        songsTableView.setItems(songDTOs);


    }


}
