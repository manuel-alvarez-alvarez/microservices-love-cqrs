package es.microcqrs.dto.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;
import java.util.UUID;

/**
 * The shipment has been shipped
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ShipmentShipped {

    private final UUID id;
    private final Date timestamp;

    public ShipmentShipped(@JsonProperty("id") UUID id, @JsonProperty("timestamp") Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
