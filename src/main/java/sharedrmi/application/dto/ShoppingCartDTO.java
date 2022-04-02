//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartDTO implements Serializable {
    private final String ownerId;
    private final List<LineItemDTO> lineItems;

    public ShoppingCartDTO(String ownerId, List<LineItemDTO> lineItems) {
        this.ownerId = ownerId;
        this.lineItems = lineItems;
    }

    public static ShoppingCartDTO.ShoppingCartDTOBuilder builder() {
        return new ShoppingCartDTO.ShoppingCartDTOBuilder();
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public List<LineItemDTO> getLineItems() {
        return this.lineItems;
    }

    public static class ShoppingCartDTOBuilder {
        private String ownerId;
        private List<LineItemDTO> lineItems;

        ShoppingCartDTOBuilder() {
        }

        public ShoppingCartDTO.ShoppingCartDTOBuilder ownerId(String ownerId) {
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
