package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Sessions, UUID> {
    List<Sessions> findByTenantId(UUID tenantId);
}
