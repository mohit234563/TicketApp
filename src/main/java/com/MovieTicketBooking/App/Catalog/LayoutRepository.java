package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LayoutRepository extends JpaRepository<Layouts, UUID> {

    Optional<Layouts> findByIdAndTenantId(UUID id,UUID tenantId);
    List<Layouts> findByTenantId(UUID tenantId);
}
