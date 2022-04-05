package sharedrmi.application.dto;

import sharedrmi.domain.enums.MediumType;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceLineItemDTO implements Serializable {
    private final MediumType mediumType;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public InvoiceLineItemDTO(MediumType mediumType, String name, int quantity, BigDecimal price) {
        this.mediumType = mediumType;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static InvoiceLineItemDTO.InvoiceLineItemDTOBuilder builder() {
        return new InvoiceLineItemDTO.InvoiceLineItemDTOBuilder();
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

    public static class InvoiceLineItemDTOBuilder {
        private MediumType mediumType;
        private String name;
        private int quantity;
        private BigDecimal price;

        InvoiceLineItemDTOBuilder() {
        }

        public InvoiceLineItemDTO.InvoiceLineItemDTOBuilder mediumType(MediumType mediumType) {
            this.mediumType = mediumType;
            return this;
        }

        public InvoiceLineItemDTO.InvoiceLineItemDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InvoiceLineItemDTO.InvoiceLineItemDTOBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public InvoiceLineItemDTO.InvoiceLineItemDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public InvoiceLineItemDTO build() {
            return new InvoiceLineItemDTO(this.mediumType, this.name, this.quantity, this.price);
        }

        public String toString() {
            return "InvoiceLineItemDTO.InvoiceLineItemDTOBuilder(mediumType=" + this.mediumType + ", name=" + this.name + ", quantity=" + this.quantity + ", price=" + this.price + ")";
        }
    }
}
