package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;


import javax.naming.NoPermissionException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class MusicSearchController {

    @FXML
    private TextField musicSearchTextField;
    @FXML
    private TableView<AlbumDTO> musicView;
    @FXML
    private TableColumn<AlbumDTO, String> albumTitleCol;
    @FXML
    private TableColumn<AlbumDTO, String> releaseDateCol;
    @FXML
    private TableColumn<AlbumDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<AlbumDTO, String> priceCol;
    @FXML
    private TableColumn<AlbumDTO, String> stockCol;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView messageIconImage;
    @FXML
    private ImageView messageBoardIconImage;
    @FXML
    private ImageView settingsIconImage;

    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        String lastSearch = SessionManager.getLastSearch();

        if (!lastSearch.isBlank()) {
            musicSearchTextField.setText(lastSearch);
            populateTable(SessionManager.getLastAlbums());
        }

        NavbarIconPositioner.positionIcons(this.roles,
                                           this.cartIconImage,
                                           this.invoiceIconImage,
                                           this.messageIconImage,
                                           this.settingsIconImage
                                           );
    }

    private void populateTable(List<AlbumDTO> albums) {
        ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);
        albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        musicView.setItems(albumDTO);
    }

    @FXML
    protected void musicSearchButtonClicked() {

        try {
            String search = musicSearchTextField.getText();
            List<AlbumDTO> albums = rmiController.findAlbumsBySongTitle(search);
            SessionManager.setLastSearch(search);
            SessionManager.setLastAlbums(albums);
            populateTable(albums);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void musicViewClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
            AlbumDTO albumDTO = musicView.getSelectionModel().getSelectedItem();

            sceneSwitcher.switchSceneToProductOverviewView(e, albumDTO);
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMusicSearchView(e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToCartView (e);
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
    }

    @FXML
    protected void messageSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMessageProducerView(e);
    }

    @FXML
    protected void messageBoardSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMessageBoardView(e);
        }
    }

    @FXML
    protected void settingsSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToSettingsView(e);
    }

    @FXML
    protected void logoutSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            try {
                SessionManager.logout();
            } catch (NotLoggedInException ex) {
                ex.printStackTrace();
                return;
            }

            sceneSwitcher.switchSceneToLoginView(e);
        }
    }
}