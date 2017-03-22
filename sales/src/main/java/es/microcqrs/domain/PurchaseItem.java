package es.microcqrs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representation of the products included in a purchase
 */
@Entity
public class PurchaseItem {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "PURCHASE", referencedColumnName = "ID")
    @JsonBackReference
    private Purchase purchase;

    private UUID product;

    private BigDecimal price;

    private long amount;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseItem purchaseItem = (PurchaseItem) o;

        return id != null ? id.equals(purchaseItem.id) : purchaseItem.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
