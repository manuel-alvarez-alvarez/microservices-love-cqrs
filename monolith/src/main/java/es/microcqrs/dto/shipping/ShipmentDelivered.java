package es.microcqrs.dto.shipping;

import java.util.UUID;

/**
 * The shipment has been delivered
 */
public class ShipmentDelivered {

    private final UUID id;

    public ShipmentDelivered(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
