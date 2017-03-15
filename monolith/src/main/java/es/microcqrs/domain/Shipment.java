package es.microcqrs.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Representation of a shipment
 */
@Entity
public class Shipment {

    public enum Status {
        NEW,
        SHIPPED,
        DELIVERED
    }

    @Id
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Enumerated(EnumType.STRING)
    private Status status;

    private UUID purchase;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getPurchase() {
        return purchase;
    }

    public void setPurchase(UUID purchase) {
        this.purchase = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        return id != null ? id.equals(shipment.id) : shipment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
