package com.MovieTicketBooking.App.Inventory;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

public class SessionSeats {
    @EmbeddedId
    private SessionSeatId id;
    @Column(name = "ticket_type_id")
    private UUID ticketTypeId;
    @Column(nullable = false)
    private long price;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private SeatStatus status;
    @Column(name = "held_by_booking")
    private UUID heldByBooking;
    @Column(name = "hold_expires_at")
    private Instant holdExpiresAt;
}
