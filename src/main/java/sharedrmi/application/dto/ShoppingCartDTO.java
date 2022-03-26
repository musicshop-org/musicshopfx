package sharedrmi.application.dto;

import java.util.List;
import java.util.UUID;

public class ShoppingCartDTO {
    private final UUID ownerId;
    private List<LineItemDTO> lineItems;

    public ShoppingCartDTO(UUID ownerId, List<LineItemDTO> lineItems) {
        this.ownerId = ownerId;
        this.lineItems = lineItems;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public List<LineItemDTO> getLineItems() {
        return lineItems;
    }
}
