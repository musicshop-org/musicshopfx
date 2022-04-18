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
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MusicOverviewController {
    @FXML
    private Label albumTitleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label mediumTypeLabel;
    @FXML
    private Label releaseDateLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TableView songsTableView;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn genreCol;
    @FXML
    private TableColumn artistCol;
    @FXML
    private Button orderButton;
    @FXML
    private Button addToCartButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label invalidQtyErrorLabel;
    @FXML
    private Label addToCartErrorLabel;
    @FXML
    private Label orderSuccessLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;

    private RMIController rmiController;
    private AlbumDTO currentAlbumDTO;
    private List<Role> roles;


    private SceneSwitcher sceneSwitcher = new SceneSwitcher();


    public void setData(AlbumDTO albumDTO) {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        for (Role role : this.roles) {
            if (role.equals(Role.SALESPERSON)) {
                this.quantityLabel.setVisible(true);
                this.quantityTextField.setVisible(true);
                this.orderButton.setVisible(true);
                this.addToCartButton.setVisible(true);
                this.cartIconImage.setVisible(true);
                this.invoiceIconImage.setVisible(true);
            }
        }

        currentAlbumDTO = albumDTO;
        Set<SongDTO> songs = albumDTO.getSongs();
        albumTitleLabel.setText(albumDTO.getTitle());
        mediumTypeLabel.setText(albumDTO.getMediumType().toString());
        releaseDateLabel.setText(albumDTO.getReleaseDate().toString());
        stockLabel.setText(Integer.toString(albumDTO.getStock()));
        priceLabel.setText(albumDTO.getPrice().toString());

        Iterator<SongDTO> iter = albumDTO.getSongs().iterator();
        List<ArtistDTO> artists = iter.next().getArtists();
        ArtistDTO artist = artists.get(0);
        artistLabel.setText(artist.getName());

        ObservableList<SongDTO> songDTOs = FXCollections.observableArrayList(songs);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artists"));

        songsTableView.setItems(songDTOs);
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMusicSearchView(e);
        }
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToCartView(e);
        }
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
        }
    }

    @FXML
    protected void addToCartButtonClicked(ActionEvent event) throws IOException {

        int qty = Integer.parseInt(quantityTextField.getText());

        if (qty < 1) {
            this.showInvalidQtyErrorLabel();
        } else if (qty > Integer.parseInt(stockLabel.getText())) {
            this.showAddToCartErrorLabel();
        } else {
            rmiController.addProductToCart(currentAlbumDTO, Integer.parseInt(quantityTextField.getText()));
            sceneSwitcher.switchSceneToMusicSearchView(event);
        }

    }

    @FXML
    protected void orderButtonClicked(ActionEvent actionEvent) throws RemoteException {

        int qty = Integer.parseInt(quantityTextField.getText());

        if (qty < 1) {
            this.showInvalidQtyErrorLabel();
        } else {

            // TODO :: publish message

            // /

            rmiController.increaseStockOfAlbum(
                    currentAlbumDTO.getTitle(),
                    currentAlbumDTO.getMediumType(),
                    qty
            );

            this.showOrderSuccessLabel();
        }

    }

    protected void showInvalidQtyErrorLabel() {
        orderSuccessLabel.setVisible(false);
        addToCartErrorLabel.setVisible(false);
        invalidQtyErrorLabel.setVisible(true);
    }

    protected void showAddToCartErrorLabel() {
        orderSuccessLabel.setVisible(false);
        invalidQtyErrorLabel.setVisible(false);
        addToCartErrorLabel.setVisible(true);
    }

    protected void showOrderSuccessLabel() {
        invalidQtyErrorLabel.setVisible(false);
        addToCartErrorLabel.setVisible(false);
        orderSuccessLabel.setVisible(true);
    }

}
