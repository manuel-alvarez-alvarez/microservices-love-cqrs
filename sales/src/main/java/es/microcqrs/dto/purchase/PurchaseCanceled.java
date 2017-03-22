package es.microcqrs.dto.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 * Event thrown when a purchase is cancelled
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class PurchaseCanceled {

    private final UUID id;
    private final String reason;

    @JsonCreator
    public PurchaseCanceled(@JsonProperty("id") UUID id, @JsonProperty("reason") String reason) {
        this.id = id;
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }
}
