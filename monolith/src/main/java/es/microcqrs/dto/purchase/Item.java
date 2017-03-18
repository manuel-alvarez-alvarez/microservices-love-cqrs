package es.microcqrs.dto.purchase;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Item part of a purchase.
 */
public class Item {

    private UUID product;
    private long amount;
    private BigDecimal price;

    public long getAmount() {
        return amount;
    }

    public void setAmount(final long amount) {
        this.amount = amount;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(final UUID product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
