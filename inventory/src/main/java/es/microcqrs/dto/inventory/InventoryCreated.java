package es.microcqrs.dto.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 * Event thrown when we have a new inventory
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class InventoryCreated {

    private final UUID id;
    private final UUID product;
    private final long available;

    @JsonCreator
    public InventoryCreated(@JsonProperty("id") UUID id,
                            @JsonProperty("product") UUID product,
                            @JsonProperty("available") long available) {
        this.id = id;
        this.product = product;
        this.available = available;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProduct() {
        return product;
    }

    public long getAvailable() {
        return available;
    }
}
