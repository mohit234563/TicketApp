package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Sessions, UUID> {
    Optional<Sessions>findByIdAndTenantId(UUID id,UUID tenantId);
    Optional<Sessions>findByTenantId(UUID tenantId);
}
