package sharedrmi.domain;

import javafx.scene.image.ImageView;
import sharedrmi.application.dto.CartLineItemDTO;
import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class CartLineItem {

    private final String name;
    private final MediumType medium;
    private final int stock;
    private final int quantity;
    private final BigDecimal price;
    private final ImageView minus_image;
    private final String plus;
    private final ImageView x_image;
    private CartLineItemDTO cartLineItemDTO;

    public CartLineItem(String name, MediumType medium, int stock, int quantity, BigDecimal price, ImageView minus_image, String plus, ImageView x_image, CartLineItemDTO cartLineItemDTO) {
        this.name = name;
        this.medium = medium;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.minus_image = minus_image;
        this.plus = plus;
        this.x_image = x_image;
        this.cartLineItemDTO = cartLineItemDTO;
    }

    public String getName() {
        return name;
    }

    public MediumType getMedium() {
        return medium;
    }

    public int getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImageView getMinus_image() {
        return minus_image;
    }

    public String getPlus() {
        return plus;
    }

    public ImageView getX_image() {
        return x_image;
    }

    public CartLineItemDTO getLineItemDTO() {
        return cartLineItemDTO;
    }
}
