package sharedrmi.domain;

import javafx.scene.image.ImageView;
import sharedrmi.application.dto.CartLineItemDTO;
import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class InvoiceLineItem {
    private String albumTitle;
    private MediumType mediumType;
    private BigDecimal price;
    private int quantity;
    private String minus;
    private int returnQuantity;
    private String plus;
    private int returnedQuantity;

    public InvoiceLineItem(String albumTitle, MediumType mediumType, BigDecimal price, int quantity, int returnQuantity, int returnedQuantity) {
        this.albumTitle = albumTitle;
        this.mediumType = mediumType;
        this.price = price;
        this.quantity = quantity;
        this.minus = "-";
        this.returnQuantity = returnQuantity;
        this.plus = "+";
        this.returnedQuantity = returnedQuantity;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public MediumType getMediumType() {
        return mediumType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMinus() {
        return minus;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public String getPlus() {
        return plus;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }
}
