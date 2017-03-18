package es.microcqrs.service;

import es.microcqrs.domain.Inventory;
import es.microcqrs.dto.inventory.InventoryAdded;
import es.microcqrs.dto.inventory.InventoryCreated;
import es.microcqrs.dto.inventory.InventoryRemoved;
import es.microcqrs.repository.InventoryRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * Read model service for the inventory
 */
@EnableBinding(Processor.class)
public class InventoryService extends AbstractReadmodelService {

    @Resource
    private InventoryRepository inventoryRepository;

    public Flux<Inventory> getInventoryItems() {
        return executeQuery(inventoryRepository::streamAll);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryCreated'")
    protected void onInventoryCreated(InventoryCreated inventoryCreated) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryCreated.getId());
        inventory.setProduct(inventoryCreated.getProduct());
        inventory.setAvailable(inventoryCreated.getAvailable());
        inventoryRepository.save(inventory);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryAdded'")
    protected void onInventoryAdded(InventoryAdded inventoryAdded) {
        Inventory inventory = inventoryRepository.findOne(inventoryAdded.getId());
        inventory.setAvailable(inventory.getAvailable() + inventoryAdded.getAmount());
        inventoryRepository.save(inventory);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='InventoryRemoved'")
    protected void onInventoryRemoved(InventoryRemoved inventoryRemoved) {
        Inventory inventory = inventoryRepository.findOne(inventoryRemoved.getId());
        inventory.setAvailable(inventory.getAvailable() - inventoryRemoved.getAmount());
        inventoryRepository.save(inventory);
    }
}