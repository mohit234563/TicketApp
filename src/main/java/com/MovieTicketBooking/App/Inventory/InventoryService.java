package com.MovieTicketBooking.App.Inventory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private static final Duration HOLD_TIME = Duration.ofMinutes(10);

    private final SessionSeatRepository repo;

    public InventoryService(SessionSeatRepository repo) {
        this.repo = repo;
    }

    /** Call when a session goes on sale. Safe to call twice. */
    @Transactional
    public int generateInventory(UUID sessionId) {
        return repo.generateForSession(sessionId);
    }

    /** The booking row must already exist (status pending) in the same transaction. */
    @Transactional
    public Instant holdSeats(UUID sessionId, UUID bookingId, List<UUID> seatIds) {
        Instant expiresAt = Instant.now().plus(HOLD_TIME);
        int held = repo.holdSeats(sessionId, seatIds, bookingId, expiresAt);
        if (held != seatIds.size()) {
            // Rolls back the whole transaction, so no partial holds remain
            throw new SeatUnavailableException("One or more seats were just taken. Please pick again.");
        }
        return expiresAt;
    }

    @Transactional
    public void confirm(UUID bookingId, int expectedSeats) {
        if (repo.confirmSeats(bookingId) != expectedSeats) {
            throw new SeatUnavailableException("Hold expired before payment completed.");
        }
    }

    @Transactional
    public void release(UUID bookingId) {
        repo.releaseByBooking(bookingId);
    }

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void releaseExpiredHolds() {
        repo.releaseExpired();
    }
}