package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;
@Entity
@Table(name="seat_layouts",uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id","id"}))
@Getter@Setter@NoArgsConstructor
public class Layouts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenants tenant;
    @Column(nullable = false)
    private String name;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "layout_json",columnDefinition = "jsonb")
    private Map<String,Object> layoutJson;
}
