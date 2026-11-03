package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Identity.Tenants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="venues",uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id","city"}))
@Getter@Setter@NoArgsConstructor
public class Venues {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenants tenant;
    @Column(nullable = false)
    private String name;
    private String city;
    private String address;
}
