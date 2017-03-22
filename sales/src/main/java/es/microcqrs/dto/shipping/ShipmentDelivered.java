package es.microcqrs.dto.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;
import java.util.UUID;

/**
 * The shipment has been delivered
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ShipmentDelivered {

    private final UUID id;
    private final Date timestamp;

    @JsonCreator
    public ShipmentDelivered(@JsonProperty("id") UUID id, @JsonProperty("timestamp") Date timestamp) {
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
