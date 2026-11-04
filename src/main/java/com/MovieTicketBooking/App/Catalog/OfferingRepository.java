package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OfferingRepository extends JpaRepository<Offerings,UUID> {
    Optional<Offerings> findByIdAndTenantId(UUID id, UUID tenantId);
    Optional<Offerings>findByTenantId(UUID id);
}
