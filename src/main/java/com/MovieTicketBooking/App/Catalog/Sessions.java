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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter@Setter
@NoArgsConstructor
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="tenant_id",nullable = false)
    private Tenants tenant;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "offering_id",nullable = false)
    private Offerings offering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resources resource;
    @Column(name = "starts_at", nullable = false)
    private Instant startsAt;
    @Column(name = "ends_at", nullable = false)
    private Instant endsAt;
    @Column(name = "sales_open_at")
    private Instant salesOpenAt;
    @Column(name = "sales_close_at")
    private Instant salesCloseAt;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "session_status")
    private SessionStatus status = SessionStatus.draft;
    @Column(name = "is_seated", nullable = false)
    private boolean isSeated = true;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cancellation_policy", columnDefinition = "jsonb", nullable = false)
    private List<Map<String, Object>> cancellationPolicy = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;
}








