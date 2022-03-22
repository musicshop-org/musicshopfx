package at.fhv.musicshopfx.musicshopfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sharedrmi.application.dto.AlbumDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onMusicSearchButtonClick(){

        List<AlbumDTO> albumDTO = new LinkedList<>();
        albumDTO.add(new AlbumDTO("Title", "lable",  LocalDate.now(), "mediumType", BigDecimal.valueOf(12), 4));
        albumDTO.add(new AlbumDTO("Title2", "lable2",  LocalDate.now(), "mediumType2", BigDecimal.valueOf(15), 2));
    }

}