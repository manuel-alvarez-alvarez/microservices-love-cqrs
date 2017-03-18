package es.microcqrs.repository;

import es.microcqrs.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Repository for the catalog items.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p order by p.price desc")
    Stream<Product> streamAll();
}
