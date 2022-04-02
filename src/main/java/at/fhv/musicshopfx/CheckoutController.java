package at.fhv.musicshopfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CheckoutController {

    @FXML
    private RadioButton anonymousCustomerRadioButton;
    @FXML
    private RadioButton existingCustomerRadioButton;
    @FXML
    private RadioButton cashRadioButton;
    @FXML
    private RadioButton creditCardRadioButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private TableView customerTableView;
    @FXML
    private Button searchButton;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchScene("musicSearch-view.fxml", e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToCartView("cart-view.fxml", e);
    }

}
