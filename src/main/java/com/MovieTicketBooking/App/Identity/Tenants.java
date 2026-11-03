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

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
public class Tenants {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true,columnDefinition = "citext")
    private String slug;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true,name = "contact_email",columnDefinition="citext")
    private String email;
    @Column(nullable = false,name="contact_phone")
    private String phone;
    @CreationTimestamp
    @Column(nullable = false,name="created_at",updatable = false)
    private Instant createdAt;
    @Column(name="plan_id")
    private Short planId;
    @Column(name="business_type",nullable=false)
    private String businessType;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false,columnDefinition = "tenant_status")
    private TenantStatus status=TenantStatus.pending;
    private String gstin;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name="currency",nullable = false,length = 3)
    private String currency;
    @Column(nullable = false)
    private String timezone = "Asia/Kolkata";
}
