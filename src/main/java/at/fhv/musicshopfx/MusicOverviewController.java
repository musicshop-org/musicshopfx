package at.fhv.musicshopfx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sharedrmi.application.dto.AlbumDTO;

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


    public void setData(AlbumDTO albumDTO){
        albumTitleTextField.setText(albumDTO.getTitle());
        mediumTypeTextField.setText(albumDTO.getMediumType().toString());
        releaseDateTextField.setText(albumDTO.getReleaseDate().toString());
        stockTextField.setText(Integer.toString(albumDTO.getStock()));
        priceTextField.setText(albumDTO.getPrice().toString());

    }


}
