package es.microcqrs.controller;

import es.microcqrs.aggregate.Catalog;
import es.microcqrs.domain.Product;
import es.microcqrs.dto.catalog.AddProduct;
import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller for the API
 */
@RestController
@RequestMapping(value = "/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    @Resource
    private ProductService productService;

    @Resource
    private Catalog catalog;

    @GetMapping
    @ResponseBody
    public List<Product> catalog() {
        return productService.getProducts();
    }

    @PostMapping
    public ProductAdded addProduct(@RequestBody AddProduct request) {
        return catalog.addProduct(request);
    }

}
