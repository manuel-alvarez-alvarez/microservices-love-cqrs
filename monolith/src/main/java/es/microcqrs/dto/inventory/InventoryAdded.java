package es.microcqrs.dto.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 * Event thrown when products are added to the inventory
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class InventoryAdded {

    private final UUID id;
    private final UUID product;
    private final long amount;

    @JsonCreator
    public InventoryAdded(@JsonProperty("id") UUID id,
                          @JsonProperty("product") UUID product,
                          @JsonProperty("amount") long amount) {
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
