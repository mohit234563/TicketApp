package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venues venue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id")
    private Layouts layout;
    @Column(nullable = false)
    private String name;
    private Integer capacity;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> attributes = new HashMap<>();
}
