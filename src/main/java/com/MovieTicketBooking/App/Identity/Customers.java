package com.MovieTicketBooking.App.Identity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Table(name = "customers")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,name = "full_name")
    private String fullname;
    @Column(nullable = false,name = "password_hash")
    private String passwordHash;
    @Column(nullable = false,unique = true,columnDefinition = "citext")
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(name="is_verified",nullable = false)
    private boolean isVerified=false;
    @CreationTimestamp
    @Column(nullable = false,name = "created_at",updatable = false)
    private Instant createdAt;

}
