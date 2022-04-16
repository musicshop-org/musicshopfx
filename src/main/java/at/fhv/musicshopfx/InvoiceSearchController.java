package at.fhv.musicshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
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
    private Label invoiceErrorLabel;

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
    private ImageView cartIconImage;
    @FXML
    private ImageView messageIconImage;

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

        determineButtonStates();

        for (Role role : this.roles)
        {
            if (role.equals(Role.SALESPERSON)) {
                this.cartIconImage.setVisible(true);
                this.invoiceIconImage.setVisible(true);
            }
        }

        for (Role role : this.roles)
        {
            if (role.equals(Role.OPERATOR)) {
                if (!cartIconImage.isVisible()) {
                    cartIconImage.setVisible(true);
                    cartIconImage.setImage(messageIconImage.getImage());
                    cartIconImage.setOnMousePressed(messageIconImage.getOnMousePressed());
                    cartIconImage.setOnMouseClicked(messageIconImage.getOnMouseClicked());
                    cartIconImage.setFitHeight(26);
                    cartIconImage.setFitWidth(26);
                } else {
                    this.messageIconImage.setVisible(true);
                }
            }
        }
    }

    @FXML
    protected void InvoiceSearchButtonClicked() {

        try {

            if (Integer.parseInt(invoiceSearchTextField.getText()) < 1) {
                throw new NumberFormatException("no valid value");
            }

            invoiceDTO = rmiController.findInvoiceById(new InvoiceId(Long.parseLong(invoiceSearchTextField.getText())));
            invoiceErrorLabel.setText("");

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

        } catch (InvoiceNotFoundException | NumberFormatException e) {
            invoiceView.getItems().clear();
            determineButtonStates();

            invoiceErrorLabel.setTextFill(Paint.valueOf("red"));

            if (e instanceof InvoiceNotFoundException) {
                invoiceErrorLabel.setText("invoice not found");
            } else {
                invoiceErrorLabel.setText("no valid value");
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void invoiceLineItemEdited(MouseEvent e) {

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

            // prevents table row selection highlighting
            invoiceView.getSelectionModel().clearSelection();

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
    protected void messageSymbolClicked(MouseEvent e) throws IOException {
        if (e.isPrimaryButtonDown())
            sceneSwitcher.switchSceneToMessageProducerView(e);
    }

    @FXML
    public void returnButtonClicked(MouseEvent e) throws InvoiceNotFoundException, IOException {
        if (e.isPrimaryButtonDown() && !invoiceView.getItems().isEmpty()) {

            for (InvoiceLineItem invoiceLineItem : data) {
                rmiController.returnInvoiceLineItem(invoiceDTO.getInvoiceId(), invoiceLineItem.getInvoiceLineItemDTO(), invoiceLineItem.getReturnQuantity());
            }

            sceneSwitcher.switchSceneToMusicSearchView(e);

        }
    }

    private void determineButtonStates() {
        boolean isCartEmpty = invoiceView.getItems().isEmpty();

        returnButton.setDisable(isCartEmpty);
    }
}
