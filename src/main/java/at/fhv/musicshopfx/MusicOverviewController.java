package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
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
    private Button addToCartButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label addToCartLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private ImageView cartIconImage;
    @FXML
    private ImageView invoiceIconImage;
    @FXML
    private ImageView messageBoardIconImage;

    private RMIController rmiController;
    private AlbumDTO currentAlbumDTO;
    private List<Role> roles;


    private SceneSwitcher sceneSwitcher = new SceneSwitcher();


    public void setData(AlbumDTO albumDTO){

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        for (Role role : this.roles)
        {
            if (role.equals(Role.SALESPERSON)) {
                this.quantityLabel.setVisible(true);
                this.quantityTextField.setVisible(true);
                this.addToCartButton.setVisible(true);
                this.cartIconImage.setVisible(true);
                this.invoiceIconImage.setVisible(true);
            }
        }

        if (!this.roles.isEmpty()) {
            this.messageBoardIconImage.setVisible(true);
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
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMusicSearchView(e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToCartView(e);
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
    }

    @FXML
    protected void messageBoardSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMessageBoardView(e);
        }
    }

    @FXML
    private void addToCartButtonClicked(ActionEvent event){

        try {
            rmiController.addProductToCart(currentAlbumDTO, Integer.parseInt(quantityTextField.getText()));

            if (Integer.parseInt(quantityTextField.getText()) < 1)
                throw new NumberFormatException();

            sceneSwitcher.switchSceneToMusicSearchView(event);

        } catch(NumberFormatException e) {
            addToCartLabel.setTextFill(Paint.valueOf("red"));
            addToCartLabel.setText("no valid value");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
