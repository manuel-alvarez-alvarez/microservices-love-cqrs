package es.microcqrs.aggregate;

import es.microcqrs.dto.catalog.AddProduct;
import es.microcqrs.dto.catalog.ProductAdded;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import java.util.UUID;

/**
 * Aggregate for the catalog
 */
@EnableBinding(Processor.class)
public class Catalog extends BaseAggregate {

    public ProductAdded addProduct(AddProduct request) {
        ProductAdded productAdded = new ProductAdded(UUID.randomUUID(), request.getName(), request.getPrice(), request.getPicture());
        return send(productAdded);
    }

}
