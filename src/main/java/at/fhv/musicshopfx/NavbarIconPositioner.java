package at.fhv.musicshopfx;

import javafx.scene.image.ImageView;
import sharedrmi.domain.valueobjects.Role;

import java.util.List;

public class NavbarIconPositioner {

    public static void positionIcons(List<Role> roles,
                                ImageView cartIconImage,
                                ImageView invoiceIconImage,
                                ImageView messageIconImage,
                                ImageView settingsIconImage)
    {
        for (Role role : roles) {

            if (role.equals(Role.SALESPERSON)) {
                // salesperson
                cartIconImage.setVisible(true);
                invoiceIconImage.setVisible(true);
            }
        }

        for (Role role : roles) {

            if (role.equals(Role.OPERATOR)) {

                if (!cartIconImage.isVisible()) {

                    // only operator -> move message to pos 2
                    cartIconImage.setVisible(true);
                    cartIconImage.setImage(messageIconImage.getImage());
                    cartIconImage.setOnMousePressed(messageIconImage.getOnMousePressed());
                    cartIconImage.setOnMouseClicked(messageIconImage.getOnMouseClicked());
                    cartIconImage.setFitHeight(26);
                    cartIconImage.setFitWidth(26);

                    // only operator -> move settings to pos 3
                    settingsIconImage.setVisible(false);
                    invoiceIconImage.setVisible(true);
                    invoiceIconImage.setImage(settingsIconImage.getImage());
                    invoiceIconImage.setOnMousePressed(settingsIconImage.getOnMousePressed());
                    invoiceIconImage.setOnMouseClicked(settingsIconImage.getOnMouseClicked());
                    invoiceIconImage.setFitHeight(28);
                    invoiceIconImage.setFitWidth(28);
                } else {
                    // operator & salesperson
                    messageIconImage.setVisible(true);
                }
            }
        }

        // only salesperson -> move settings to pos 4
        if (!messageIconImage.isVisible() && settingsIconImage.isVisible()) {
            settingsIconImage.setVisible(false);
            messageIconImage.setVisible(true);
            messageIconImage.setImage(settingsIconImage.getImage());
            messageIconImage.setOnMousePressed(settingsIconImage.getOnMousePressed());
            messageIconImage.setOnMouseClicked(settingsIconImage.getOnMouseClicked());
            messageIconImage.setFitHeight(30);
            messageIconImage.setFitWidth(30);
        }
    }
}
