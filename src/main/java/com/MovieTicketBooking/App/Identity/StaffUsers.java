package com.MovieTicketBooking.App.Identity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.Instant;
import java.util.UUID;

@Table(name = "staff_users",uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id","email"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class StaffUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="full_name",nullable = false)
    private String fullname;
    @Column(name = "password_hash",nullable = false)
    private String passwordHash;
    @Column(name = "email",nullable = false,columnDefinition = "citext")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenants tenant;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition="staff_role",nullable = false)
    private StaffRole role;
    @Column(name = "is_active",nullable = false)
    private boolean isActive;
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;
}
