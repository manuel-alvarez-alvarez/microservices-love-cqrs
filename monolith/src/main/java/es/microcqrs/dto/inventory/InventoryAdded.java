package es.microcqrs.dto.inventory;

import java.util.UUID;

/**
 * Event thrown when products are added to the inventory
 */
public class InventoryAdded {

    private final UUID id;
    private final UUID product;
    private final long amount;

    public InventoryAdded(UUID id, UUID product, long amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    public UUID getProduct() {
        return product;
    }

    public long getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }
}
