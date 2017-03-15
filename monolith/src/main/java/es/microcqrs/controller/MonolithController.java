package es.microcqrs.controller;

import es.microcqrs.domain.Inventory;
import es.microcqrs.domain.Product;
import es.microcqrs.domain.Purchase;
import es.microcqrs.domain.Shipment;
import es.microcqrs.dto.catalog.AddProduct;
import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.dto.inventory.AddToInventory;
import es.microcqrs.dto.inventory.InventoryAdded;
import es.microcqrs.dto.inventory.InventoryRemoved;
import es.microcqrs.dto.inventory.RemoveFromInventory;
import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseItems;
import es.microcqrs.dto.shipping.DeliverShipment;
import es.microcqrs.dto.shipping.ShipShipment;
import es.microcqrs.dto.shipping.ShipmentDelivered;
import es.microcqrs.dto.shipping.ShipmentShipped;
import es.microcqrs.exception.ProductNotAvailableInInventory;
import es.microcqrs.service.MonolithService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * Controller for the API
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class MonolithController {

    @Resource
    private MonolithService monolithService;

    @GetMapping("/inventory")
    public Flux<Inventory> inventory() {
        return monolithService.getInventoryItems();
    }

    @PostMapping("/inventory/add")
    public Mono<InventoryAdded> addToInventory(@RequestBody AddToInventory request) {
        return Mono.fromSupplier(() -> monolithService.addToInventory(request));
    }

    @PostMapping("/inventory/remove")
    public Mono<InventoryRemoved> removeFromInventory(@RequestBody RemoveFromInventory request) {
        return Mono.fromSupplier(() -> monolithService.removeFromInventory(request));
    }

    @GetMapping("/sales")
    public Flux<Purchase> sales() {
        return monolithService.getPurchases();
    }

    @PostMapping("/sales")
    public Mono<ItemsPurchased> purchase(@RequestBody PurchaseItems request) {
        return Mono.fromSupplier(() -> monolithService.purchase(request));
    }

    @GetMapping("/shipping")
    public Flux<Shipment> shipping() {
        return monolithService.getShipments();
    }

    @PostMapping("/shipping/ship")
    public Mono<ShipmentShipped> ship(@RequestBody ShipShipment request) {
        return Mono.fromSupplier(() -> monolithService.ship(request));
    }

    @PostMapping("/shipping/deliver")
    public Mono<ShipmentDelivered> deliver(@RequestBody DeliverShipment request) {
        return Mono.fromSupplier(() -> monolithService.deliver(request));
    }

    @GetMapping("/catalog")
    public Flux<Product> catalog() {
        return monolithService.getProducts();
    }

    @PostMapping("/catalog")
    public Mono<ProductAdded> addProduct(@RequestBody AddProduct request) {
        return Mono.fromSupplier(() -> monolithService.addProduct(request));
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ProductNotAvailableInInventory.class)
    @ResponseBody
    public String productNotAvailableInInventory(ProductNotAvailableInInventory e) {
        return "There are not enough items in our inventory";
    }

}
