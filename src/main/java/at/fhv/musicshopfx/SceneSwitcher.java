package at.fhv.musicshopfx;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sharedrmi.application.dto.AlbumDTO;

import java.io.IOException;

public class SceneSwitcher {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchScene(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        displayScene(event);
    }

    private void displayScene(Event event){
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double sceneWidth = ((Node) event.getSource()).getScene().getWidth();
        double sceneHeight = ((Node) event.getSource()).getScene().getHeight();
        scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(scene);
        stage.show();
    }

    public void switchSceneToCartView(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        CartController cartController = loader.getController();
        cartController.setData();
        displayScene(event);

    }

    public void switchSceneToProductOverview(String fxml, Event event, AlbumDTO albumDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        MusicOverviewController musicOverviewController = loader.getController();
        musicOverviewController.setData(albumDTO);

        displayScene(event);

    }
}
