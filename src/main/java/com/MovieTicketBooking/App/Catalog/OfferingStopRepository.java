package com.MovieTicketBooking.App.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OfferingStopRepository extends JpaRepository<OfferingStop, UUID> {
   Optional<OfferingStop>findByOfferingIdOrderBySeq(UUID id);
   @Modifying(flushAutomatically = true)
   @Query("DELETE FROM OfferingStop s where s.offeringId=:offeringId")
   void deleteByOfferingId(@Param("offeringId")UUID offeringId);
}
