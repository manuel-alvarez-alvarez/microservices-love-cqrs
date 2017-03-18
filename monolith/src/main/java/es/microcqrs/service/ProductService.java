package es.microcqrs.service;

import es.microcqrs.domain.Product;
import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.repository.ProductRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * Read model service for the products
 */
@EnableBinding(Processor.class)
public class ProductService extends AbstractReadmodelService {

    @Resource
    private ProductRepository productRepository;

    public Flux<Product> getProducts() {
        return executeQuery(productRepository::streamAll);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ProductAdded'")
    protected void onProductAdded(ProductAdded productAdded) {
        Product product = new Product();
        product.setId(productAdded.getId());
        product.setName(productAdded.getName());
        product.setPrice(productAdded.getPrice());
        product.setPicture(productAdded.getPicture());
        productRepository.save(product);
    }
}
