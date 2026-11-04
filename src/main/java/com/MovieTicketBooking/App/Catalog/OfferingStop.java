package com.MovieTicketBooking.App.Catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name="offering_stops",uniqueConstraints = @UniqueConstraint(columnNames = {"offering_id","seq"}))
@Getter
@Setter
@NoArgsConstructor
public class OfferingStop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,name = "offering_id")
    private UUID offeringId;
    @Column(name = "stop_name",nullable = false)
    private String stopName;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private int seq;
    @Column(nullable = false,name = "minutes_from_strat")
    private int minutesFromStart=0;
    @Column(nullable = false,name = "is_boarding")
    private boolean isBoarding=true;
    @Column(nullable = false,name = "is_dropping")
    private boolean isDropping=true;
}