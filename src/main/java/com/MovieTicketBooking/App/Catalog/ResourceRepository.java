package com.MovieTicketBooking.App.Catalog;

import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resources, UUID> {
    Optional<Resources>findBYIdAndTenantId(UUID id,UUID tenantId);
    Optional<Resources>findByTenantId(UUID tenantID);
}
