package es.microcqrs.repository;

import es.microcqrs.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Repository for the sales.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    @Query("select p from Purchase p order by p.sold desc")
    Stream<Purchase> streamAll();
}
