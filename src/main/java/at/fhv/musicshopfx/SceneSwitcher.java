package at.fhv.musicshopfx;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.CartLineItemDTO;

import java.io.IOException;
import java.util.List;

public class SceneSwitcher {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final String cartViewFxml = "cart-view.fxml";
    private final String checkoutViewFxml = "checkout-view.fxml";
    private final String musicSearchViewFxml = "musicSearch-view.fxml";
    private final String productOverviewViewFxml = "productOverview-view.fxml";
    private final String loginViewFxml = "login-view.fxml";


    private void displayScene(Event event){
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double sceneWidth = ((Node) event.getSource()).getScene().getWidth();
        double sceneHeight = ((Node) event.getSource()).getScene().getHeight();
        scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(scene);
        stage.show();
    }

    public void switchSceneToCartView(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(cartViewFxml));
        root = loader.load();

        CartController cartController = loader.getController();
        cartController.setData();
        displayScene(event);

    }

    public void switchSceneToMusicSearchView(Event e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(musicSearchViewFxml));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setMaxWidth(99999);
        stage.setMaxHeight(99999);

        MusicSearchController musicSearchController = loader.getController();
        musicSearchController.setData();
        displayScene(e);
    }

    public void switchSceneToCheckoutView(Event e, List<CartLineItemDTO> cartLineItemDTOs) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(checkoutViewFxml));
        root = loader.load();

        CheckoutController checkoutController = loader.getController();
        checkoutController.setData(cartLineItemDTOs);
        displayScene(e);
    }

    public void switchSceneToProductOverviewView(Event event, AlbumDTO albumDTO) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(productOverviewViewFxml));
        root = loader.load();

        MusicOverviewController musicOverviewController = loader.getController();
        musicOverviewController.setData(albumDTO);
        displayScene(event);

    }

    public void switchSceneToLoginView(Event e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(loginViewFxml));
        root = loader.load();
        displayScene(e);
    }
}
