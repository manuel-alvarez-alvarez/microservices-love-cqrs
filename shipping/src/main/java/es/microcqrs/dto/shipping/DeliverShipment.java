package es.microcqrs.dto.shipping;

import java.util.UUID;

/**
 * Request to deliver the shipment
 */
public class DeliverShipment {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
