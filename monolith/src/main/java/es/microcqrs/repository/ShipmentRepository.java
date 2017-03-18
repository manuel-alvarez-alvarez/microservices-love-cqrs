package es.microcqrs.repository;

import es.microcqrs.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Repository for the shipments
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

    @Query("select s from Shipment s order by s.timestamp desc")
    Stream<Shipment> streamAll();
}
