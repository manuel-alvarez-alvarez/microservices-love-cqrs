package es.microcqrs.dto.shipping;

import java.util.UUID;

/**
 * The shipment has been shipped
 */
public class ShipmentShipped {

    private final UUID id;

    public ShipmentShipped(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
