package com.MovieTicketBooking.App.Catalog;

import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resources, UUID> {
    Optional<Resources>findByIdAndTenantId(UUID id,UUID tenantId);
    List<Resources> findByTenantId(UUID tenantID);
}
