package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@NoArgsConstructor
public class Offerings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,name = "tenant_id")
    private Tenants tenant;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory category;
    @Column(nullable = false)
    private String title;
    private  String description;
    @Column(name = "poster_url")
    private String posterUrl;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> attributes;
    @Column(name = "is_active")
    private boolean isActive;
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;

}
//id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
//tenant_id        UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
//category_id      SMALLINT NOT NULL REFERENCES service_categories(id),
//title            TEXT NOT NULL,                    -- "Inception", "Jaipur -> Delhi", "Coldplay Live"
//description      TEXT,
//poster_url       TEXT,
//attributes       JSONB NOT NULL DEFAULT '{}',      -- genre/language/duration | bus type/amenities
//is_active        BOOLEAN NOT NULL DEFAULT true,
//created_at       TIMESTAMPTZ NOT NULL DEFAULT now()