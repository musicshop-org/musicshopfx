package at.fhv.musicshopfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setTitle("Music Shop");
        stage.setScene(scene);
        stage.setMaxHeight(535);
        stage.setMinHeight(535);
        stage.setMaxWidth(815);
        stage.setMinWidth(815);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}