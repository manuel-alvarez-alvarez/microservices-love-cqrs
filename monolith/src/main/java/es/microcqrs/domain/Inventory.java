package es.microcqrs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Representation of an element in the getInventoryItems
 */
@Entity
public class Inventory {

    @Id
    private UUID id;

    private UUID product;

    private long available;

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventory that = (Inventory) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
