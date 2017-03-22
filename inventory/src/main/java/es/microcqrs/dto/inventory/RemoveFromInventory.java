package es.microcqrs.dto.inventory;

import java.util.UUID;

/**
 * Request to remove items from the inventory.
 */
public class RemoveFromInventory {

    private UUID id;
    private UUID product;
    private long amount;

    public UUID getProduct() {
        return product;
    }

    public void setProduct(final UUID product) {
        this.product = product;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(final long amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }
}
