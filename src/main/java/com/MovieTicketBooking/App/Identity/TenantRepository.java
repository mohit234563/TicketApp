package com.MovieTicketBooking.App.Identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenants, UUID> {
    Optional<Tenants>findBySlug(String slug);
}
