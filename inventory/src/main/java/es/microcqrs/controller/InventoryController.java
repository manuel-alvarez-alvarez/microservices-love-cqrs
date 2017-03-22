package es.microcqrs.controller;

import es.microcqrs.aggregate.Store;
import es.microcqrs.domain.Inventory;
import es.microcqrs.dto.inventory.AddToInventory;
import es.microcqrs.dto.inventory.InventoryAdded;
import es.microcqrs.dto.inventory.InventoryRemoved;
import es.microcqrs.dto.inventory.RemoveFromInventory;
import es.microcqrs.exception.ProductNotAvailableInInventory;
import es.microcqrs.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller for the API
 */
@RestController
@RequestMapping(value = "/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Resource
    private InventoryService inventoryService;

    @Resource
    private Store store;

    @GetMapping
    public List<Inventory> inventory() {
        return inventoryService.getInventoryItems();
    }

    @PostMapping("/add")
    public InventoryAdded addToInventory(@RequestBody AddToInventory request) {
        return store.addToInventory(request);
    }

    @PostMapping("/remove")
    public InventoryRemoved removeFromInventory(@RequestBody RemoveFromInventory request) {
        return store.removeFromInventory(request);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ProductNotAvailableInInventory.class)
    @ResponseBody
    public String productNotAvailableInInventory(ProductNotAvailableInInventory e) {
        return "There are not enough items in our inventory";
    }

}
