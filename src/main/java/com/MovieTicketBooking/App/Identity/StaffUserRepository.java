package com.MovieTicketBooking.App.Identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffUserRepository extends JpaRepository<StaffUsers,UUID> {
    Optional<StaffUsers>findByTenantIdAndEmail(UUID tenantId,String email);
    List<StaffUsers> findByTenantId(UUID TenantId);
}
