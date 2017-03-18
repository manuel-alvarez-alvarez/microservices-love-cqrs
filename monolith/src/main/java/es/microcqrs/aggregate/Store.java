package es.microcqrs.aggregate;

import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.dto.inventory.*;
import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseCanceled;
import es.microcqrs.exception.ProductNotAvailableInInventory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Aggregate for the inventory
 */
@EnableBinding(Processor.class)
public class Store extends BaseAggregate {

    private Map<UUID, InventoryData> inventories = new LinkedHashMap<>();
    private Map<UUID, UUID> inventoriesByProduct = new LinkedHashMap<>();

    public InventoryAdded addToInventory(AddToInventory request) {
        validateChangeOfInventory(request.getId(), request.getProduct(), request.getAmount());
        InventoryAdded inventoryAdded = new InventoryAdded(request.getId(), request.getProduct(), request.getAmount());
        return send(inventoryAdded);
    }

    public InventoryRemoved removeFromInventory(RemoveFromInventory request) {
        validateChangeOfInventory(request.getId(), request.getProduct(), (-1) * request.getAmount());
        InventoryRemoved inventoryRemoved = new InventoryRemoved(request.getId(), request.getProduct(), request.getAmount());
        return send(inventoryRemoved);
    }

    private void validateChangeOfInventory(UUID inventory, UUID product, long amount) {
        InventoryData data = this.inventories.get(inventory);
        long before = data.getAvailable();
        long after = before + amount;
        if (after < 0) {
            throw new ProductNotAvailableInInventory(product, amount, before);
        }
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ProductAdded'")
    private void onProductAdded(ProductAdded productAdded) {
        InventoryCreated inventoryCreated = new InventoryCreated(UUID.randomUUID(), productAdded.getId(), 0L);
        send(inventoryCreated);
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryCreated'")
    private void onInventoryCreated(InventoryCreated inventoryCreated) {
        InventoryData data = new InventoryData(inventoryCreated.getProduct());
        data.setAvailable(inventoryCreated.getAvailable());
        this.inventories.put(inventoryCreated.getId(), data);
        this.inventoriesByProduct.put(data.getProduct(), inventoryCreated.getId());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryRemoved'")
    private void onInventoryRemoved(InventoryRemoved inventoryRemoved) {
        InventoryData data = this.inventories.get(inventoryRemoved.getId());
        long before = data.getAvailable();
        data.setAvailable(before - inventoryRemoved.getAmount());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryAdded'")
    private void onInventoryAdded(InventoryAdded inventoryAdded) {
        InventoryData data = this.inventories.get(inventoryAdded.getId());
        long before = data.getAvailable();
        data.setAvailable(before + inventoryAdded.getAmount());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ItemsPurchased'")
    private void onItemsPurchased(ItemsPurchased itemsPurchased) {
        try {
            List<RemoveFromInventory> removed = itemsPurchased.getItems()
                    .stream()
                    .map(item -> {
                        long bought = item.getAmount();
                        UUID inventory = this.inventoriesByProduct.get(item.getProduct());
                        InventoryData data = this.inventories.get(inventory);
                        long left = data.getAvailable() - bought;
                        if (left < 0) {
                            throw new ProductNotAvailableInInventory(item.getProduct(), bought, data.getAvailable());
                        }
                        RemoveFromInventory removeFromInventory = new RemoveFromInventory();
                        removeFromInventory.setId(inventory);
                        removeFromInventory.setProduct(item.getProduct());
                        removeFromInventory.setAmount(item.getAmount());
                        return removeFromInventory;
                    })
                    .collect(Collectors.toList());
            removed.forEach(this::removeFromInventory);
        } catch (ProductNotAvailableInInventory e) {
            PurchaseCanceled purchaseCanceled = new PurchaseCanceled(itemsPurchased.getId(), "The availability of the products changed, we are a terrible store");
            send(purchaseCanceled);
        }

    }

    private static class InventoryData {
        private final UUID product;
        private long available;

        public InventoryData(UUID product) {
            this.product = product;
        }

        public UUID getProduct() {
            return product;
        }

        public long getAvailable() {
            return available;
        }

        public void setAvailable(long available) {
            this.available = available;
        }
    }
}
