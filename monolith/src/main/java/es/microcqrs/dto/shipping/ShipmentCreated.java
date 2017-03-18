package es.microcqrs.dto.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;
import java.util.UUID;

/**
 * Event thrown when there is a new shipment
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ShipmentCreated {

    private final UUID id;
    private final UUID purchase;
    private final Date timestamp;

    @JsonCreator
    public ShipmentCreated(@JsonProperty("id") UUID id, @JsonProperty("purchase") UUID purchase, @JsonProperty("timestamp") Date timestamp) {
        this.id = id;
        this.purchase = purchase;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPurchase() {
        return purchase;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
