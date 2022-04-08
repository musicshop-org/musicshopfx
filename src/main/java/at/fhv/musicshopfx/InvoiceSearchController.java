package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.InvoiceDTO;
import sharedrmi.application.dto.InvoiceLineItemDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.InvoiceId;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class InvoiceSearchController {

    @FXML
    private TextField invoiceSearchTextField;
    @FXML
    private TableView<InvoiceLineItemDTO> invoiceView;
    @FXML
    private TableColumn<InvoiceLineItemDTO, String> albumTitleCol;
    @FXML
    private TableColumn<InvoiceLineItemDTO, String> mediumTypeCol;
    @FXML
    private TableColumn<InvoiceLineItemDTO, String> priceCol;
    @FXML
    private TableColumn<InvoiceLineItemDTO, String> quantityCol;
    @FXML
    private TableColumn<AlbumDTO, String> returnQuantityCol;
    @FXML
    private TableColumn<AlbumDTO, String> returnedQuantityCol;
    @FXML
    private Button returnButton;
    @FXML
    private ImageView invoiceIconImage;

    private RMIController rmiController;
    private List<Role> roles;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    public void setData() {
        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();

        } catch (NotLoggedInException | RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        for (Role role : this.roles)
        {
            if (role.equals(Role.SALESPERSON)) {
                this.invoiceIconImage.setVisible(true);
            }
        }
    }

    @FXML
    protected void InvoiceSearchButtonClicked() {

        try {

            Optional<InvoiceDTO> invoiceDTO = rmiController.findInvoiceById(new InvoiceId(Long.parseLong(invoiceSearchTextField.getText())));
            List<InvoiceLineItemDTO> invoiceLineItems = invoiceDTO.get().getInvoiceLineItems();

            ObservableList<InvoiceLineItemDTO> invoiceLineItemDTO = FXCollections.observableArrayList(invoiceLineItems);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            invoiceView.setItems(invoiceLineItemDTO);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMusicSearchView(e);
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToCartView (e);
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
    }

    @FXML
    public void invoiceViewClicked(MouseEvent e) {
    }

    @FXML
    public void returnButtonClicked(ActionEvent e) {
    }
}
