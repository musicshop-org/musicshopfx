package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.communication.rmi.RMIControllerFactory;


import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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

    private RMIController rmiController;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {
        try {
            this.rmiController = SessionManager.getInstance().getRMIController();

        } catch (NotLoggedInException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    protected void MusicSearchButtonClicked() {

        //TODO: only call Naming.lookup at startup and add error handling
        try {
            RMIControllerFactory rmiControllerFactory = (RMIControllerFactory) Naming.lookup("rmi://localhost/RMIControllerFactory");
            RMIController rmiController = rmiControllerFactory.createRMIController(USERNAME, PASSWORD);
            List<AlbumDTO> albums = rmiController.findAlbumsBySongTitle(musicSearchTextField.getText());

            ObservableList<AlbumDTO> albumDTO = FXCollections.observableArrayList(albums);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            musicView.setItems(albumDTO);

        } catch (NotBoundException | MalformedURLException | RemoteException | FailedLoginException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void musicViewClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
            AlbumDTO albumDTO = musicView.getSelectionModel().getSelectedItem();

            sceneSwitcher.switchSceneToProductOverview("productOverview-view.fxml", e, albumDTO);
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            switchSceneToCartView ("cart-view.fxml", e);
    }

    private void switchSceneToMusicSearchView (String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        MusicSearchController musicSearchController = loader.getController();
        musicSearchController.setData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    private void switchSceneToProductOverview(String fxml, Event event, AlbumDTO albumDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        MusicOverviewController musicOverviewController = loader.getController();
        musicOverviewController.setData(albumDTO);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}