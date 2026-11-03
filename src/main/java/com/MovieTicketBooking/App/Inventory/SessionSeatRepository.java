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
    @Query(nativeQuery = true,value = """
        Insert into session_seats(session_id,seat_id,ticket_type_id,price)
        select distinct on (s.id) se.id,s.id,tt.id,COALESCE(tt.base_pricer,0)
        from sessions se
            join resources r on r.id = se.resource_id
            join seats s on s.layout_id=r.layout_id
            left join ticket_types_tt
                on tt.session_id=se.id
                and (tt.section=s.section or tt.section is null)
            where se.id =:sessionId
            order by s.id,(tt.session is null)
        on conflict do nothing
""")
    int generateForSession(@Param("sessionId")UUID sessionId);

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
