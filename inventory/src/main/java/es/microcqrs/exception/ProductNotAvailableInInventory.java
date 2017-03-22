package es.microcqrs.exception;

import java.util.UUID;

/**
 * Exception thrown when there is not enough products in the inventory
 */
public class ProductNotAvailableInInventory extends RuntimeException {

    private final UUID product;
    private final long bought;
    private final long available;

    public ProductNotAvailableInInventory(final UUID product, final long bought, final long available) {
        this.product = product;
        this.bought = bought;
        this.available = available;
    }

    public UUID getProduct() {
        return product;
    }

    public long getBought() {
        return bought;
    }

    public long getAvailable() {
        return available;
    }
}
