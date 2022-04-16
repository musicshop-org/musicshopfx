package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView messageIconImage;

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

        for (Role role : this.roles)
        {
            if (role.equals(Role.SALESPERSON)) {
                this.cartIconImage.setVisible(true);
                this.invoiceIconImage.setVisible(true);
            }
        }

        for (Role role : this.roles)
        {
            if (role.equals(Role.OPERATOR)) {
                this.messageIconImage.setVisible(true);
            }
        }
    }

    private void populateTable(List<AlbumDTO> albums) {
        ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);
        albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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
    protected void messageButtonClicked() throws RemoteException {

        String testTopic = "Test_Topic";
        String testTopic2 = "Test_Topic2";
        String testTitle = "Test_Title";
        String testMessage = "This is a Test-Message :)";
        long expirationDays = 14;
        this.rmiController.publish(List.of(testTopic, testTopic2), testTitle, testMessage, expirationDays);
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
    protected void logoutButtonClicked(ActionEvent e) throws IOException {
        try {
            SessionManager.logout();
        } catch (NotLoggedInException ex) {
            ex.printStackTrace();
            return;
        }

        sceneSwitcher.switchSceneToLoginView(e);
    }

}