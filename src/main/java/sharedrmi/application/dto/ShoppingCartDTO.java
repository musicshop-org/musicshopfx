//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ShoppingCartDTO implements Serializable {
    private final UUID ownerId;
    private final List<LineItemDTO> lineItems;

    public ShoppingCartDTO(UUID ownerId, List<LineItemDTO> lineItems) {
        this.ownerId = ownerId;
        this.lineItems = lineItems;
    }

    public static ShoppingCartDTO.ShoppingCartDTOBuilder builder() {
        return new ShoppingCartDTO.ShoppingCartDTOBuilder();
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    public List<LineItemDTO> getLineItems() {
        return this.lineItems;
    }

    public static class ShoppingCartDTOBuilder {
        private UUID ownerId;
        private List<LineItemDTO> lineItems;

        ShoppingCartDTOBuilder() {
        }

        public ShoppingCartDTO.ShoppingCartDTOBuilder ownerId(UUID ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public ShoppingCartDTO.ShoppingCartDTOBuilder lineItems(List<LineItemDTO> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public ShoppingCartDTO build() {
            return new ShoppingCartDTO(this.ownerId, this.lineItems);
        }

        public String toString() {
            return "ShoppingCartDTO.ShoppingCartDTOBuilder(ownerId=" + this.ownerId + ", lineItems=" + this.lineItems + ")";
        }
    }
}
