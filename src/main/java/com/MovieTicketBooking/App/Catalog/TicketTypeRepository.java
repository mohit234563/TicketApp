package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketTypeRepository extends JpaRepository<TicketTypes, UUID> {
    List<TicketTypeRepository>findBySessionId(UUID sessionId);
}
