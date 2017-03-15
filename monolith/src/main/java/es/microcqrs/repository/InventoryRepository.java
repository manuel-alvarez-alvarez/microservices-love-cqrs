package es.microcqrs.repository;

import es.microcqrs.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Repository for the inventory
 */
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    @Query("select i from Inventory i")
    Stream<Inventory> streamAll();

    List<Inventory> findByProductIn(Collection<UUID> products);
}
