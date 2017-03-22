package es.microcqrs.repository;

import es.microcqrs.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for the inventory
 */
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

}
