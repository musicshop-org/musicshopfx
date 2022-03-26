package sharedrmi.application.dto;

import sharedrmi.domain.enums.MediumType;

import java.math.BigDecimal;

public class LineItemDTO {
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

    public MediumType getMediumType() {
        return mediumType;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
