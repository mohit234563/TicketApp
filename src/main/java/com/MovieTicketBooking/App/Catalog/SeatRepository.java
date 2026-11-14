package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seats, UUID>{
    List<Seats> findByLayoutId(UUID layoutId);
    long countByLayoutId(UUID layoutId);
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM Seats s WHERE s.layout.id = :layout_id")
    void deleteByLayoutId(@Param("layout_id")UUID layoutId);
}
