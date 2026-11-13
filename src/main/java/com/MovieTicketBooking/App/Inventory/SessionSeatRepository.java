package com.MovieTicketBooking.App.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;


import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SessionSeatRepository extends JpaRepository<SessionSeats,SessionSeatId> {
    @Query("select s from SessionSeats s where s.id.sessionId = :sessionId")
    List<SessionSeats> findBySession(@Param("sessionId") UUID sessionId);

    @Modifying
    @Query(nativeQuery = true, value = """
    INSERT INTO session_seats (session_id, seat_id, ticket_type_id, price)
    SELECT DISTINCT ON (s.id) se.id, s.id, tt.id, COALESCE(tt.base_price, 0)
      FROM sessions se
      JOIN resources r ON r.id = se.resource_id
      JOIN seats s     ON s.layout_id = r.layout_id
      LEFT JOIN ticket_types tt
             ON tt.session_id = se.id
            AND (tt.section = s.section OR tt.section IS NULL)
     WHERE se.id = :sessionId
     ORDER BY s.id, (tt.section IS NULL)
    ON CONFLICT DO NOTHING
    """)
    int generateForSession(@Param("sessionId") UUID sessionId);

    @Modifying
    @Query(nativeQuery = true,value = """
        UPDATE session_seats
            SET status='held',held_by_booking=:bookingId,hold_expires_at=:expiresAt
        where session_id=:sessionId
            AND seat_id in (:seatIds)
            AND (status='available' OR (status='held' AND hold_expires_at<now()))
""")
    int holdSeats(@Param("sessionId")UUID sessionId,
                  @Param("seatIds") Collection<UUID> seats,
                  @Param("bookingId")UUID bookingId,
                  @Param("expiresAt") Instant expiresAt);

    //after payment succeeds
    @Modifying
    @Query(nativeQuery = true,value = """
        UPDATE session_seats
            SET status='booked',hold_expires_at=NULL
        WHERE held_by_booking=:bookingId AND status='held'
        """)
    int confirmSeats(@Param("bookingId")UUID bookingId);

    // Payment failed or customer cancelled
    @Modifying
    @Query(nativeQuery = true,value = """
        UPDATE session_seats
            SET status='available',held_by_booking=NULL,hold_expires_at=NULL
        where held_by_booking=:bookingId AND status='held'
        """)
    int releaseByBooking(@Param("bookingId")UUID bookingId);

    //scheduled cleanup
    @Modifying
    @Query(nativeQuery = true,value = """
        Update session_seats
            SET status='available',held_by_booking=NULL,hold_expires_at=NULL
        where status='held' AND hold_expires_at<now()
""")
    int releaseExpired();
}
