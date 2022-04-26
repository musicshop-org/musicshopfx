//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import sharedrmi.domain.enums.MediumType;

public class CartLineItemDTO implements Serializable {
    private final MediumType mediumType;
    private final String name;
    private final int quantity;
    private final BigDecimal price;
    private final int stock;

    public CartLineItemDTO(MediumType mediumType, String name, int quantity, BigDecimal price, int stock) {
        this.mediumType = mediumType;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
    }

    public static CartLineItemDTO.CartLineItemDTOBuilder builder() {
        return new CartLineItemDTO.CartLineItemDTOBuilder();
    }

    public MediumType getMediumType() {
        return this.mediumType;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public static class CartLineItemDTOBuilder {
        private MediumType mediumType;
        private String name;
        private int quantity;
        private BigDecimal price;
        private int stock;

        CartLineItemDTOBuilder() {
        }

        public CartLineItemDTO.CartLineItemDTOBuilder mediumType(MediumType mediumType) {
            this.mediumType = mediumType;
            return this;
        }

        public CartLineItemDTO.CartLineItemDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CartLineItemDTO.CartLineItemDTOBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartLineItemDTO.CartLineItemDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CartLineItemDTO.CartLineItemDTOBuilder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public CartLineItemDTO build() {
            return new CartLineItemDTO(this.mediumType, this.name, this.quantity, this.price, this.stock);
        }

        public String toString() {
            return "CartLineItemDTO.CartLineItemDTOBuilder(mediumType=" + this.mediumType + ", name=" + this.name + ", quantity=" + this.quantity + ", price=" + this.price + ", stock=" + this.stock + ")";
        }
    }
}
