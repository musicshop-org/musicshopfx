package at.fhv.musicshopfx;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.domain.valueobjects.Role;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class NavbarIconPositioner {

    private final static SceneSwitcher sceneSwitcher = new SceneSwitcher();

    private final  String BASE_IMAGE_PATH = "src/main/resources/at/fhv/musicshopfx/images/";
    private final  String SEARCH_IMAGE = BASE_IMAGE_PATH + "search.png";
    private final  String CART_IMAGE = BASE_IMAGE_PATH + "shopping-Cart.png";
    private final  String INVOICE_IMAGE = BASE_IMAGE_PATH + "invoice.png";
    private final  String PUBLISH_IMAGE = BASE_IMAGE_PATH + "writeMessage.png";
    private final  String MESSAGE_IMAGE = BASE_IMAGE_PATH + "envelope.png";
    private final  String NEW_MESSAGE_IMAGE = BASE_IMAGE_PATH + "envelopered.png";
    private final  String SETTINGS_IMAGE = BASE_IMAGE_PATH + "settings.png";

    private ImageView searchIcon;
    private ImageView cartIcon;
    private ImageView invoiceIcon;
    private ImageView publishIcon;
    private ImageView messageIcon;
    private ImageView newMessageIcon;
    private ImageView settingsIcon;

    private RMIController rmiController;
    private List<Role> roles;

    public NavbarIconPositioner() {

        try {
            this.rmiController = SessionManager.getInstance().getRMIController();
            this.roles = rmiController.getRoles();
            this.searchIcon = getImageView(SEARCH_IMAGE, 30, 30);
            this.cartIcon = getImageView(CART_IMAGE, 30, 30);
            this.invoiceIcon = getImageView(INVOICE_IMAGE, 30, 30);
            this.publishIcon = getImageView(PUBLISH_IMAGE, 30, 30);
            this.messageIcon = getImageView(MESSAGE_IMAGE, 30, 30);
            this.newMessageIcon = getImageView(NEW_MESSAGE_IMAGE, 30, 30);
            this.settingsIcon = getImageView(SETTINGS_IMAGE, 30, 30);
            this.searchIcon.setPickOnBounds(true);
            this.cartIcon.setPickOnBounds(true);
            this.invoiceIcon.setPickOnBounds(true);
            this.publishIcon.setPickOnBounds(true);
            this.messageIcon.setPickOnBounds(true);
            this.newMessageIcon.setPickOnBounds(true);
            this.settingsIcon.setPickOnBounds(true);
            this.searchIcon.setCursor(Cursor.HAND);
            this.cartIcon.setCursor(Cursor.HAND);
            this.invoiceIcon.setCursor(Cursor.HAND);
            this.publishIcon.setCursor(Cursor.HAND);
            this.messageIcon.setCursor(Cursor.HAND);
            this.newMessageIcon.setCursor(Cursor.HAND);
            this.settingsIcon.setCursor(Cursor.HAND);

        } catch (FileNotFoundException | NotLoggedInException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void positionIcons(VBox navbarVbox) throws FileNotFoundException {

        searchIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.isPrimaryButtonDown()) {
                    try {
                        sceneSwitcher.switchSceneToMusicSearchView(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        VBox.setMargin(searchIcon, new Insets(15.0, 0.0, 15.0, 15.0));
        navbarVbox.getChildren().add(searchIcon);

        if(roles.contains(Role.SALESPERSON)) {
            cartIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        try {
                            sceneSwitcher.switchSceneToCartView(mouseEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            VBox.setMargin(cartIcon, new Insets(15.0, 0.0, 15.0, 15.0));
            navbarVbox.getChildren().add(cartIcon);


            invoiceIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        try {
                            sceneSwitcher.switchSceneToInvoiceSearchView(mouseEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            VBox.setMargin(invoiceIcon, new Insets(15.0, 0.0, 15.0, 15.0));
            navbarVbox.getChildren().add(invoiceIcon);
        }

        if(roles.contains(Role.OPERATOR)) {
            publishIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        try {
                            sceneSwitcher.switchSceneToMessageProducerView(mouseEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            VBox.setMargin(publishIcon, new Insets(15.0, 0.0, 15.0, 15.0));
            navbarVbox.getChildren().add(publishIcon);
        }
        if (SessionManager.isNewMessageAvailable()) {
            newMessageIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        try {
                            sceneSwitcher.switchSceneToMessageBoardView(mouseEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            VBox.setMargin(newMessageIcon, new Insets(15.0, 0.0, 15.0, 15.0));
            navbarVbox.getChildren().add(newMessageIcon);


        } else {
            messageIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        try {
                            sceneSwitcher.switchSceneToMessageBoardView(mouseEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            VBox.setMargin(messageIcon, new Insets(15.0, 0.0, 15.0, 15.0));
            navbarVbox.getChildren().add(messageIcon);
        }

        settingsIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.isPrimaryButtonDown()) {
                    try {
                        sceneSwitcher.switchSceneToSettingsView(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        VBox.setMargin(settingsIcon, new Insets(15.0, 0.0, 15.0, 15.0));
        navbarVbox.getChildren().add(settingsIcon);
    }

    private static ImageView getImageView(String pathToImage, int height, int width) throws FileNotFoundException {
        FileInputStream inpStr = new FileInputStream(pathToImage);

        Image image = new Image(inpStr);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        return imageView;
    }
}
