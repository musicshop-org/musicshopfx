package at.fhv.musicshopfx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sharedrmi.application.dto.AlbumDTO;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Label loginFailedLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    protected void login(ActionEvent e) throws IOException {
        if (SessionManager.login(usernameTextField.getText(), passwordTextField.getText())) {
            switchScene("musicSearch-view.fxml", e);
        } else {
            loginFailedLabel.setText("wrong username or password entered");
            passwordTextField.clear();
        }
    }

    private void switchScene(String fxml, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
