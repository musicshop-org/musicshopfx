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
            loginFailedLabel.setText(SessionManager.getErrorMessage());
            passwordTextField.clear();
        }
    }
}
