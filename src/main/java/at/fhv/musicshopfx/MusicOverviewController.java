package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sharedrmi.application.api.ShoppingCartService;
import sharedrmi.application.api.ShoppingCartServiceFactory;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.communication.rmi.RMIControllerFactory;

import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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

    private AlbumDTO currentAlbumDTO;

    private final String USERNAME = "essiga";
    private final String PASSWORD = "password01";

//    private final String USERNAME = "prescherm";
//    private final String PASSWORD = "password02";

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void setData(AlbumDTO albumDTO){
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
            switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchSceneToCartView("cart-view.fxml", e);
    }

    private void switchScene(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addToCartButtonClicked(ActionEvent event){

        try {
            RMIControllerFactory rmiControllerFactory = (RMIControllerFactory) Naming.lookup("rmi://localhost/RMIControllerFactory");
            RMIController rmiController = rmiControllerFactory.createRMIController(USERNAME, PASSWORD);
            rmiController.addProductToCart(currentAlbumDTO, Integer.parseInt(quantityTextField.getText()));

            if (Integer.parseInt(quantityTextField.getText()) < 1)
                throw new NumberFormatException();

//            quantityTextField.setText("");
//            addToCartLabel.setTextFill(Paint.valueOf("green"));
//            addToCartLabel.setText("added to cart");

              switchScene("musicSearch-view.fxml", event);

        } catch(NumberFormatException e) {
            addToCartLabel.setTextFill(Paint.valueOf("red"));
            addToCartLabel.setText("no valid value");
        }
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        } catch (FailedLoginException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneToCartView(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        CartController cartController = loader.getController();
        cartController.setData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
