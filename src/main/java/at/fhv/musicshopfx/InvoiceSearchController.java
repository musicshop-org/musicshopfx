package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sharedrmi.application.dto.InvoiceDTO;
import sharedrmi.application.dto.InvoiceLineItemDTO;
import sharedrmi.application.exceptions.InvoiceNotFoundException;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.InvoiceLineItem;
import sharedrmi.domain.valueobjects.InvoiceId;
import sharedrmi.domain.valueobjects.Role;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceSearchController {

    @FXML
    private TextField invoiceSearchTextField;

    @FXML
    private TableView<InvoiceLineItem> invoiceView;
    @FXML
    private TableColumn<InvoiceLineItem, String> albumTitleCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> mediumTypeCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> priceCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> quantityCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> minusCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> returnQuantityCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> plusCol;
    @FXML
    private TableColumn<InvoiceLineItem, String> returnedQuantityCol;

    @FXML
    private Button returnButton;
    @FXML
    private ImageView invoiceIconImage;

    private ObservableList<InvoiceLineItem> data;
    private InvoiceDTO invoiceDTO;

    private final int MINUS_COLUMN_POSITION = 4;
    private final int PLUS_COLUMN_POSITION = 6;

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

        for (Role role : this.roles) {
            if (role.equals(Role.SALESPERSON)) {
                this.invoiceIconImage.setVisible(true);
            }
        }

        determineButtonStates();
    }

    @FXML
    protected void InvoiceSearchButtonClicked() {

        try {

            invoiceDTO = rmiController.findInvoiceById(new InvoiceId(Long.parseLong(invoiceSearchTextField.getText())));

            if (invoiceDTO == null) {
                throw new InvoiceNotFoundException("invoice not found");
            }

            List<InvoiceLineItemDTO> invoiceLineItemsDTO = invoiceDTO.getInvoiceLineItems();

            // translate List<LineItemDTO> to List<CartLineItem>
            List<InvoiceLineItem> invoiceLineItems = invoiceLineItemsDTO
                    .stream()
                    .map(item -> new InvoiceLineItem(
                            item.getName(),
                            item.getMediumType(),
                            item.getPrice(),
                            item.getQuantity(),
                            0,
                            item.getReturnedQuantity(),
                            item
                    ))
                    .collect(Collectors.toList());

            ObservableList<InvoiceLineItem> obsDTOs = FXCollections.observableArrayList(invoiceLineItems);

            albumTitleCol.setCellValueFactory(new PropertyValueFactory<>("albumTitle"));
            mediumTypeCol.setCellValueFactory(new PropertyValueFactory<>("mediumType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            minusCol.setCellValueFactory(new PropertyValueFactory<>("minus"));
            returnQuantityCol.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
            plusCol.setCellValueFactory(new PropertyValueFactory<>("plus"));
            returnedQuantityCol.setCellValueFactory(new PropertyValueFactory<>("returnedQuantity"));

            data = obsDTOs;

            invoiceView.setItems(obsDTOs);

        } catch (Exception e) {

            invoiceView.getItems().clear();
            determineButtonStates();

            e.printStackTrace();
        }
    }

    @FXML
    protected void cartLineItemEdited(MouseEvent e) {

        if (e.isPrimaryButtonDown() && !invoiceView.getItems().isEmpty()) {

            InvoiceLineItem invoiceLineItem = invoiceView.getSelectionModel().getSelectedItem();

            // get selected row-index
            int selectedRowIdx = invoiceView.getSelectionModel().getSelectedIndex();

            // get selected column-index
            ObservableList<TablePosition> pos = invoiceView.getSelectionModel().getSelectedCells();

            int selectedColIdx = -1;

            for (TablePosition po : pos) {
                selectedColIdx = po.getColumn();
            }

            int returnQty = invoiceLineItem.getReturnQuantity();

            // minus clicked
            if (selectedColIdx == MINUS_COLUMN_POSITION) {

                if (returnQty > 0) {
                    int newReturnQty = returnQty - 1;

                    data.set(selectedRowIdx, new InvoiceLineItem(
                            invoiceLineItem.getAlbumTitle(),
                            invoiceLineItem.getMediumType(),
                            invoiceLineItem.getPrice(),
                            invoiceLineItem.getQuantity(),
                            newReturnQty,
                            invoiceLineItem.getReturnedQuantity(),
                            invoiceLineItem.getInvoiceLineItemDTO()
                    ));

                    returnQty = newReturnQty;
                }

            }

            // plus clicked
            else if (selectedColIdx == PLUS_COLUMN_POSITION) {

                if (returnQty < invoiceLineItem.getQuantity() - invoiceLineItem.getReturnedQuantity()) {
                    int newReturnQty = returnQty + 1;

                    data.set(selectedRowIdx, new InvoiceLineItem(
                            invoiceLineItem.getAlbumTitle(),
                            invoiceLineItem.getMediumType(),
                            invoiceLineItem.getPrice(),
                            invoiceLineItem.getQuantity(),
                            newReturnQty,
                            invoiceLineItem.getReturnedQuantity(),
                            invoiceLineItem.getInvoiceLineItemDTO()
                    ));

                    returnQty = newReturnQty;
                }

            }

            returnButton.setDisable(returnQty <= 0);
        }
    }

    @FXML
    protected void searchSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToMusicSearchView(e);
        }
    }

    @FXML
    protected void cartSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToCartView(e);
        }
    }

    @FXML
    protected void invoiceSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown()) {
            sceneSwitcher.switchSceneToInvoiceSearchView(e);
        }
    }

    @FXML
    public void returnButtonClicked(MouseEvent e) throws Exception {
        if (e.isPrimaryButtonDown() && !invoiceView.getItems().isEmpty()) {
            InvoiceLineItem invoiceLineItem = invoiceView.getSelectionModel().getSelectedItem();

            rmiController.returnInvoiceLineItem(invoiceDTO.getInvoiceId(), invoiceLineItem.getInvoiceLineItemDTO(), invoiceLineItem.getReturnQuantity());
        }
    }

    private void determineButtonStates() {
        boolean isCartEmpty = invoiceView.getItems().isEmpty();

        returnButton.setDisable(isCartEmpty);
    }
}
