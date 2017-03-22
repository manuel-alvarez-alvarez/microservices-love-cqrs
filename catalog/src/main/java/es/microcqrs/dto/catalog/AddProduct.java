package es.microcqrs.dto.catalog;

import java.math.BigDecimal;

/**
 * Request to add a new product
 */
public class AddProduct {

    private String name;
    private BigDecimal price;
    private String picture;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
