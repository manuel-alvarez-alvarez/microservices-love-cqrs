package es.microcqrs.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Representation of a sale
 */
@Entity
public class Purchase {

    public enum Status {
        NEW,
        SHIPPED,
        DELIVERED,
        CANCELED
    }

    @Id
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PurchaseItem> items;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sold;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal price;

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getSold() {
        return sold;
    }

    public void setSold(final Date sold) {
        this.sold = sold;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        return id != null ? id.equals(purchase.id) : purchase.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
