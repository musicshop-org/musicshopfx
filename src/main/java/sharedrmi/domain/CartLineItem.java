package sharedrmi.domain;

import javafx.scene.image.ImageView;
import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class CartLineItem {

    private String title;
    private String artist;
    private MediumType medium;
    private int quantity;
    private BigDecimal price;
    private ImageView image;


    public CartLineItem(String title, String artist, MediumType medium, int quantity, BigDecimal price, ImageView image) {
        this.title = title;
        this.artist = artist;
        this.medium = medium;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public MediumType getMedium() {
        return medium;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImageView getImage() {
        return image;
    }
}
