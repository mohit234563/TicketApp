package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VenueRepository extends JpaRepository<Venues, UUID> {
    Optional<Venues>findByIdAndTenantId(UUID id,UUID tenantId);
    Optional<Venues>findBYTenantId(UUID tenantId);
}
