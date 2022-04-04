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
        stage.show();

//        final String USERNAME = "essiga";
//        final String PASSWORD = "password01";

//        final String USERNAME = "prescher";
//        final String PASSWORD = "password02";

//        System.out.println(SessionManager.login(USERNAME, PASSWORD));
//        SessionManager sessionManager = SessionManager.getInstance();
//        System.out.println(sessionManager.getRMIController().getUsername());
//        System.out.println(sessionManager.getRMIController().getRoles());
    }

    public static void main(String[] args) {
        launch();
    }

}