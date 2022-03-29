//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import sharedrmi.domain.enums.MediumType;

public class LineItemDTO implements Serializable {
    private final MediumType mediumType;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public LineItemDTO(MediumType mediumType, String name, int quantity, BigDecimal price) {
        this.mediumType = mediumType;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static LineItemDTO.LineItemDTOBuilder builder() {
        return new LineItemDTO.LineItemDTOBuilder();
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

    public static class LineItemDTOBuilder {
        private MediumType mediumType;
        private String name;
        private int quantity;
        private BigDecimal price;

        LineItemDTOBuilder() {
        }

        public LineItemDTO.LineItemDTOBuilder mediumType(MediumType mediumType) {
            this.mediumType = mediumType;
            return this;
        }

        public LineItemDTO.LineItemDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LineItemDTO.LineItemDTOBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public LineItemDTO.LineItemDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public LineItemDTO build() {
            return new LineItemDTO(this.mediumType, this.name, this.quantity, this.price);
        }

        public String toString() {
            return "LineItemDTO.LineItemDTOBuilder(mediumType=" + this.mediumType + ", name=" + this.name + ", quantity=" + this.quantity + ", price=" + this.price + ")";
        }
    }
}
