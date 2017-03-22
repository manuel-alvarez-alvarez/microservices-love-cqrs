package es.microcqrs.repository;

import es.microcqrs.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for the catalog items.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
