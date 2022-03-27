package sharedrmi.domain;

import javafx.scene.image.ImageView;
import sharedrmi.application.dto.LineItemDTO;
import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class CartLineItem {

    private String name;
    private MediumType medium;
    private int quantity;
    private BigDecimal price;
    private ImageView minus_image;
    private ImageView plus_image;
    private ImageView x_image;
    private LineItemDTO lineItemDTO;

    public CartLineItem(String name, MediumType medium, int quantity, BigDecimal price, ImageView minus_image, ImageView plus_image, ImageView x_image, LineItemDTO lineItemDTO) {
        this.name = name;
        this.medium = medium;
        this.quantity = quantity;
        this.price = price;
        this.minus_image = minus_image;
        this.plus_image = plus_image;
        this.x_image = x_image;
        this.lineItemDTO = lineItemDTO;
    }

    public String getName() {
        return name;
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

    public ImageView getMinus_image() {
        return minus_image;
    }

    public ImageView getPlus_image() {
        return plus_image;
    }

    public ImageView getX_image() {
        return x_image;
    }

    public LineItemDTO getLineItemDTO() {
        return lineItemDTO;
    }
}
