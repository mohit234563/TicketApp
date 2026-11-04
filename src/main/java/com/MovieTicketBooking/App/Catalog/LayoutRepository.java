package com.MovieTicketBooking.App.Catalog;

import ch.qos.logback.core.Layout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LayoutRepository extends JpaRepository<Layout, UUID> {

    Optional<Layout> findByIdAndTenantId(UUID id,UUID tenantId);
    Optional<Layout>findByTenantId(UUID tenantId);
}
