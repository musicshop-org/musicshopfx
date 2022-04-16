package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MessageProducerController {


    @FXML
    private TextField MessageTitleTextField;

    @FXML
    private ImageView searchIconImage;

    @FXML
    private ImageView cartIconImage;

    @FXML
    private Label expirationTextField;

    @FXML
    private ImageView invoiceIconImage;

    @FXML
    private TextArea messageTextField;

    @FXML
    private Button publishButton;

    @FXML
    private TableColumn<String, CheckBox> publishCol;

    @FXML
    private TableColumn<String, String> topicCol;

    @FXML
    private TableView<?> topicView;


    @FXML
    void searchSymbolClicked(MouseEvent event) {

    }

    @FXML
    void cartSymbolClicked(MouseEvent event) {

    }

    @FXML
    void invoiceSymbolClicked(MouseEvent event) {

    }

    @FXML
    void publishButtonClicked(ActionEvent event) {

    }
}
