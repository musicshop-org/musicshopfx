package sharedrmi.domain;

import javafx.scene.image.ImageView;
import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class CartLineItem {

    private String title;
    private String artist;
    private MediumType medium;
    private ImageView minus_image;
    private int quantity;
    private ImageView plus_image;
    private BigDecimal price;
    private ImageView x_image;

    public CartLineItem(String title, String artist, MediumType medium, ImageView minus_image, int quantity, ImageView plus_image, BigDecimal price, ImageView x_image) {
        this.title = title;
        this.artist = artist;
        this.medium = medium;
        this.minus_image = minus_image;
        this.quantity = quantity;
        this.plus_image = plus_image;
        this.price = price;
        this.x_image = x_image;
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

    public ImageView getMinus_image() {
        return minus_image;
    }

    public int getQuantity() {
        return quantity;
    }

    public ImageView getPlus_image() {
        return plus_image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImageView getX_image() {
        return x_image;
    }
}
