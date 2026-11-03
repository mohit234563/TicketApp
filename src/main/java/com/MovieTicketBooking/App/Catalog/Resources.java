package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenants tenant;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "venue_id")
    private Venues venue;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "layout_id")
    private Layouts layout;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int capacity;
    @JdbcTypeCode(SqlTypes.JSON)
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
