package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Sessions, UUID> {
    Optional<Sessions>findByIdAndTenantId(UUID id ,UUID tenantId);
    List<Sessions> findByTenantId(UUID tenantId);
    List<Sessions>findByTenantIdAndOrderByStartAtDesc(UUID id);
    List<Sessions>findByTenantIdAndOfferingIdOrderByStartAtDesc(UUID tenantId,UUID offeringId);
    boolean existsByResource_layout_id(UUID layoutId);
    boolean existsByResource_id(UUID resourceId);
}
