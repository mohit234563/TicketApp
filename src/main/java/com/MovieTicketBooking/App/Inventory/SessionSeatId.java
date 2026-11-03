package com.MovieTicketBooking.App.Inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter@AllArgsConstructor@NoArgsConstructor@EqualsAndHashCode
public class SessionSeatId implements Serializable {
    @Column(name = "session_id")
    private UUID sessionId;
    @Column(name = "seat_id")
    private UUID seatId;
}
