package es.microcqrs.dto.inventory;

import java.util.UUID;

/**
 * Event thrown when products are remove from the inventory
 */
public class InventoryRemoved {

    private final UUID id;
    private final UUID product;
    private final long amount;

    public InventoryRemoved(UUID id, UUID product, long amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProduct() {
        return product;
    }

    public long getAmount() {
        return amount;
    }


}
