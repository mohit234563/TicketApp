package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenants tenant;
    @JoinColumn(name = "venue_id",nullable = false)
    private Venues venue;
    @JoinColumn(name = "layout_id")
    private Layouts layout;
    @Column(nullable = false)
    private String name;
    private int capacity;
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> attributes;
}
//CREATE TABLE resources (
//        id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//tenant_id        UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
//venue_id         UUID REFERENCES venues(id),
//layout_id        UUID REFERENCES seat_layouts(id),
//name             TEXT NOT NULL,                    -- "Screen 3", "KA-01-AB-1234"
//capacity         INT,
//attributes       JSONB NOT NULL DEFAULT '{}'
//        );
