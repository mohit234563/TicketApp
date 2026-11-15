package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferingRepository extends JpaRepository<Offerings,UUID> {
    List<Offerings>findByTenantId(UUID id);
    Optional<Offerings>findByIdAndTenantId(UUID id,UUID tenantId);
}
