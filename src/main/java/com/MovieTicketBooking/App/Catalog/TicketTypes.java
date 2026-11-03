package com.MovieTicketBooking.App.Catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ticket_types")
@Getter@Setter@NoArgsConstructor
public class TicketTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "session_id",nullable = false)
    private Sessions session;
    @Column(nullable = false)
    private String name;
    private String section;
    @Column(nullable = false,name = "base_price")
    private long basePrice;
    @Column(nullable = false,name = "tax_bps")
    private int taxBPS;
    private int capacity;
    @Column(name = "sold_count",nullable = false)
    private int soldCount=0;
    @Column(name = "held_count",nullable = false)
    private int heldCount=0;
    @Column(name = "max_per_booking",nullable = false)
    private int maxPerBooking=10;
}
//CREATE TABLE ticket_types (
//        id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//session_id       UUID NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
//name             TEXT NOT NULL,
//section          TEXT,                             -- links price to a seat section (NULL = any)
//base_price       BIGINT NOT NULL CHECK (base_price >= 0),
//tax_bps          INT NOT NULL DEFAULT 0,
//capacity         INT,                              -- used for general admission only
//sold_count       INT NOT NULL DEFAULT 0,
//held_count       INT NOT NULL DEFAULT 0,
//max_per_booking  INT NOT NULL DEFAULT 10,
//CHECK (capacity IS NULL OR sold_count + held_count <= capacity)  -- prevents overselling GA
//);