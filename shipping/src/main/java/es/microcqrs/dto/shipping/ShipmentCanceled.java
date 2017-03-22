package es.microcqrs.dto.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;
import java.util.UUID;

/**
 * Event thrown when a shipment is canceled
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ShipmentCanceled {

    private final UUID id;
    private final String reason;
    private final Date timestamp;

    @JsonCreator
    public ShipmentCanceled(@JsonProperty("id") UUID id, @JsonProperty("reason") String reason, @JsonProperty("timestamp") Date timestamp) {
        this.id = id;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
