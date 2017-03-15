package es.microcqrs.service;

import es.microcqrs.domain.*;
import es.microcqrs.dto.catalog.AddProduct;
import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.dto.inventory.AddToInventory;
import es.microcqrs.dto.inventory.InventoryAdded;
import es.microcqrs.dto.inventory.InventoryRemoved;
import es.microcqrs.dto.inventory.RemoveFromInventory;
import es.microcqrs.dto.purchase.Item;
import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseItems;
import es.microcqrs.dto.shipping.DeliverShipment;
import es.microcqrs.dto.shipping.ShipShipment;
import es.microcqrs.dto.shipping.ShipmentDelivered;
import es.microcqrs.dto.shipping.ShipmentShipped;
import es.microcqrs.exception.ProductNotAvailableInInventory;
import es.microcqrs.repository.InventoryRepository;
import es.microcqrs.repository.ProductRepository;
import es.microcqrs.repository.PurchaseRepository;
import es.microcqrs.repository.ShipmentRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Monolithic service
 */
@Service
public class MonolithService {

    @Resource
    private ProductRepository productRepository;
    @Resource
    private PurchaseRepository purchaseRepository;
    @Resource
    private ShipmentRepository shipmentRepository;
    @Resource
    private InventoryRepository inventoryRepository;

    @Resource
    private ConversionService conversionService;
    @Resource
    private PlatformTransactionManager txtManager;

    public Flux<Inventory> getInventoryItems() {
        return executeQuery(inventoryRepository::streamAll);
    }

    @Transactional
    public InventoryAdded addToInventory(AddToInventory request) {
        changeInventory(request.getId(), request.getAmount());
        return new InventoryAdded(request.getId(), request.getProduct(), request.getAmount());
    }

    @Transactional
    public InventoryRemoved removeFromInventory(RemoveFromInventory request) {
        changeInventory(request.getId(), (-1) * request.getAmount());
        return new InventoryRemoved(request.getId(), request.getProduct(), request.getAmount());
    }

    public Flux<Purchase> getPurchases() {
        return executeQuery(purchaseRepository::streamAll);
    }


    @Transactional
    public ItemsPurchased purchase(PurchaseItems request) {
        Map<UUID, Product> products = productRepository.findAll(request.getItems().stream().map(Item::getProduct).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        Purchase purchase = createPurchase(request, products);
        createShipment(purchase);
        removeFromInventory(purchase, products);
        return new ItemsPurchased(purchase, conversionService);
    }

    public Flux<Shipment> getShipments() {
        return executeQuery(shipmentRepository::streamAll);
    }


    @Transactional
    public ShipmentShipped ship(ShipShipment request) {
        Shipment shipment = shipmentRepository.findOne(request.getId());
        updateShipment(shipment, Shipment.Status.SHIPPED);
        updatePurchase(shipment, Purchase.Status.SHIPPED);
        return new ShipmentShipped(shipment.getId());
    }

    @Transactional
    public ShipmentDelivered deliver(DeliverShipment request) {
        Shipment shipment = shipmentRepository.findOne(request.getId());
        updateShipment(shipment, Shipment.Status.DELIVERED);
        updatePurchase(shipment, Purchase.Status.DELIVERED);
        return new ShipmentDelivered(shipment.getId());
    }

    public Flux<Product> getProducts() {
        return executeQuery(productRepository::streamAll);
    }

    @Transactional
    public ProductAdded addProduct(AddProduct request) {
        Product product = createProduct(request);
        createEmptyInventory(product);
        return new ProductAdded(product.getId(), product.getName(), product.getPrice(), product.getPicture());
    }

    private Product createProduct(AddProduct request) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setPicture(request.getPicture());
        return productRepository.save(product);
    }

    private Inventory createEmptyInventory(Product product) {
        Inventory inventory = new Inventory();
        inventory.setId(UUID.randomUUID());
        inventory.setProduct(product.getId());
        inventory.setAvailable(0L);
        return inventoryRepository.save(inventory);
    }

    private Purchase createPurchase(PurchaseItems request, Map<UUID, Product> products) {
        Purchase purchase = new Purchase();
        purchase.setId(UUID.randomUUID());
        purchase.setStatus(Purchase.Status.NEW);
        purchase.setItems(request.getItems().stream().map(it -> conversionService.convert(it, PurchaseItem.class)).collect(Collectors.toList()));
        purchase.getItems().forEach(it -> {
            it.setId(UUID.randomUUID());
            it.setPurchase(purchase);
            it.setPrice(products.get(it.getProduct()).getPrice().multiply(new BigDecimal(it.getAmount())));
        });
        purchase.setSold(new Date());
        purchase.setPrice(purchase.getItems().stream().map(PurchaseItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        return purchaseRepository.save(purchase);
    }

    private Shipment createShipment(Purchase purchase) {
        Shipment shipment = new Shipment();
        shipment.setId(UUID.randomUUID());
        shipment.setStatus(Shipment.Status.NEW);
        shipment.setTimestamp(new Date());
        shipment.setPurchase(purchase.getId());
        return shipmentRepository.save(shipment);
    }

    private void removeFromInventory(Purchase purchase, Map<UUID, Product> products) {
        List<Inventory> inventories = inventoryRepository.findByProductIn(products.keySet());
        Map<UUID, PurchaseItem> purchased = purchase.getItems().stream().collect(Collectors.toMap(PurchaseItem::getProduct, Function.identity()));
        inventories.forEach(inventory -> {
            long bought = purchased.get(inventory.getProduct()).getAmount();
            long left = inventory.getAvailable() - bought;
            if (left < 0) {
                throw new ProductNotAvailableInInventory(inventory.getProduct(), bought, inventory.getAvailable());
            }
            inventory.setAvailable(left);
        });
        inventoryRepository.save(inventories);
    }

    private void updateShipment(Shipment shipment, Shipment.Status status) {
        shipment.setTimestamp(new Date());
        shipment.setStatus(status);
        shipmentRepository.save(shipment);
    }

    private void updatePurchase(Shipment shipment, Purchase.Status status) {
        Purchase purchase = purchaseRepository.findOne(shipment.getPurchase());
        purchase.setStatus(status);
        purchaseRepository.save(purchase);
    }

    private void changeInventory(UUID id, long amount) {
        Inventory inventory = inventoryRepository.findOne(id);
        long before = inventory.getAvailable();
        inventory.setAvailable(inventory.getAvailable() + amount);
        if (inventory.getAvailable() < 0) {
            throw new ProductNotAvailableInInventory(inventory.getProduct(), amount, before);
        }
        inventoryRepository.save(inventory);
    }

    private <I> Flux<I> executeQuery(Supplier<Stream<I>> supplier) {
        TransactionTemplate txt = new TransactionTemplate();
        txt.setReadOnly(true);
        TransactionStatus status = this.txtManager.getTransaction(txt);
        Stream<I> stream = supplier.get();
        return Flux
                .fromIterable(stream::iterator)
                .doOnTerminate(stream::close)
                .doOnTerminate((() -> this.txtManager.commit(status)));
    }
}
