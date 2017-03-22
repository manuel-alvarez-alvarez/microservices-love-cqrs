package es.microcqrs.dto.shipping;

import java.util.UUID;

/**
 * Lets ship the shipment
 */
public class ShipShipment {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
