package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label loginFailedLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();



    @FXML
    protected void login(ActionEvent e) throws IOException {
        if (SessionManager.login(usernameTextField.getText(), passwordTextField.getText())) {
            sceneSwitcher.switchSceneToMusicSearchView(e);
        } else {
            loginFailedLabel.setText("wrong username or password");
            passwordTextField.clear();
        }
    }

//    private void switchSceneToMusicSearchView (String fxml, Event event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//        root = loader.load();
//
//        MusicSearchController musicSearchController = loader.getController();
//        musicSearchController.setData();
//
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
