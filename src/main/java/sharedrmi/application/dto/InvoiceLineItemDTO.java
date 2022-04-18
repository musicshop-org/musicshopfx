//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import sharedrmi.domain.enums.MediumType;

public class InvoiceLineItemDTO implements Serializable {
    private final MediumType mediumType;
    private final String name;
    private final int quantity;
    private final BigDecimal price;
    private final int returnedQuantity;

    public InvoiceLineItemDTO(MediumType mediumType, String name, int quantity, BigDecimal price, int returnedQuantity) {
        this.mediumType = mediumType;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.returnedQuantity = returnedQuantity;
    }

    public static List<InvoiceLineItemDTO> createFromCartLineItemDTOs(List<CartLineItemDTO> cartLineItemDTOs) {
        return cartLineItemDTOs
                .stream()
                .map((cartLineItemDTO) -> builder()
                        .mediumType(cartLineItemDTO.getMediumType())
                        .name(cartLineItemDTO.getName())
                        .quantity(cartLineItemDTO.getQuantity())
                        .price(cartLineItemDTO.getPrice())
                        .returnedQuantity(0)
                        .build())
                .collect(Collectors.toList());
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

    public int getReturnedQuantity() {
        return this.returnedQuantity;
    }

    public static class InvoiceLineItemDTOBuilder {
        private MediumType mediumType;
        private String name;
        private int quantity;
        private BigDecimal price;
        private int returnedQuantity;

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

        public InvoiceLineItemDTO.InvoiceLineItemDTOBuilder returnedQuantity(int returnedQuantity) {
            this.returnedQuantity = returnedQuantity;
            return this;
        }

        public InvoiceLineItemDTO build() {
            return new InvoiceLineItemDTO(this.mediumType, this.name, this.quantity, this.price, this.returnedQuantity);
        }

        public String toString() {
            return "InvoiceLineItemDTO.InvoiceLineItemDTOBuilder(mediumType=" + this.mediumType + ", name=" + this.name + ", quantity=" + this.quantity + ", price=" + this.price + ", returnedQuantity=" + this.returnedQuantity + ")";
        }
    }
}
