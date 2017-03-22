package es.microcqrs.repository;

import es.microcqrs.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for the shipments
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

}
