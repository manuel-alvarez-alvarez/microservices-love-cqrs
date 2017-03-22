package es.microcqrs.repository;

import es.microcqrs.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for the sales.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
