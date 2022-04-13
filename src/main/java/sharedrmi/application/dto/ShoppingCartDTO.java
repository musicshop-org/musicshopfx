//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartDTO implements Serializable {
    private final String ownerId;
    private final List<CartLineItemDTO> cartLineItems;

    public ShoppingCartDTO(String ownerId, List<CartLineItemDTO> cartLineItems) {
        this.ownerId = ownerId;
        this.cartLineItems = cartLineItems;
    }

    public static ShoppingCartDTO.ShoppingCartDTOBuilder builder() {
        return new ShoppingCartDTO.ShoppingCartDTOBuilder();
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public List<CartLineItemDTO> getCartLineItems() {
        return this.cartLineItems;
    }

    public static class ShoppingCartDTOBuilder {
        private String ownerId;
        private List<CartLineItemDTO> cartLineItems;

        ShoppingCartDTOBuilder() {
        }

        public ShoppingCartDTO.ShoppingCartDTOBuilder ownerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public ShoppingCartDTO.ShoppingCartDTOBuilder cartLineItems(List<CartLineItemDTO> cartLineItems) {
            this.cartLineItems = cartLineItems;
            return this;
        }

        public ShoppingCartDTO build() {
            return new ShoppingCartDTO(this.ownerId, this.cartLineItems);
        }

        public String toString() {
            return "ShoppingCartDTO.ShoppingCartDTOBuilder(ownerId=" + this.ownerId + ", cartLineItems=" + this.cartLineItems + ")";
        }
    }
}
