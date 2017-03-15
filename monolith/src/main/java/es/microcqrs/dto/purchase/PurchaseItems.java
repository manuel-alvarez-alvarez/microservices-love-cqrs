package es.microcqrs.dto.purchase;

import java.util.List;

/**
 * Request to purchase the items.
 */
public class PurchaseItems {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(final List<Item> items) {
        this.items = items;
    }

}
