package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sharedrmi.application.dto.AlbumDTO;

public class InvoiceSearchController {

    @FXML
    private TextField invoiceSearchTextField;
    @FXML
    private TableView<AlbumDTO> invoiceView;
    @FXML
    private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML
    private TableColumn<AlbumDTO, String> artistNameCol;
    @FXML
    private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<AlbumDTO, String> priceCol;
    @FXML
    private TableColumn<AlbumDTO, String> quantityCol;
    @FXML
    private TableColumn<AlbumDTO, String> returnQuantityCol;
    @FXML
    private TableColumn<AlbumDTO, String> returnedQuantityCol;

}
