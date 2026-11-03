package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name="tenant_id",nullable = false)
    private Tenants tenant;
    @JoinColumn(name = "offering_id",nullable = false)
    private Offerings offering;
    @JoinColumn(name = "resource_id",nullable = false)
    private Resources resource;
    @Column(nullable = false)
    private Instant startsAt;
    @Column(nullable = false)
    private Instant endsAt;
    private Instant salesOpenAt;
    private Instant salesEndAt;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
   private SessionStatus status;
    @Column(nullable = false,name = "is_seated")
    private boolean isSeated;
    @Column(name = "cancellation_policy",columnDefinition = "jsonb")
    private Map<String,Object> cancellationPolicy;
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;
}
//
//CREATE TABLE sessions (
//        id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//tenant_id        UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
//offering_id      UUID NOT NULL REFERENCES offerings(id) ON DELETE CASCADE,
//resource_id      UUID REFERENCES resources(id),
//starts_at        TIMESTAMPTZ NOT NULL,
//ends_at          TIMESTAMPTZ NOT NULL,
//sales_open_at    TIMESTAMPTZ,
//sales_close_at   TIMESTAMPTZ,
//status           session_status NOT NULL DEFAULT 'draft',
//is_seated        BOOLEAN NOT NULL DEFAULT true,    -- false = general admission
//cancellation_policy JSONB NOT NULL DEFAULT '[]',   -- [{"hours_before":24,"refund_pct":90},...]
//created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
//CHECK (ends_at > starts_at),
//    -- a resource can't be double-booked in overlapping time ranges
//EXCLUDE USING gist (
//        resource_id WITH =,
//        tstzrange(starts_at, ends_at) WITH &&
//        ) WHERE (resource_id IS NOT NULL AND status <> 'cancelled')
//);