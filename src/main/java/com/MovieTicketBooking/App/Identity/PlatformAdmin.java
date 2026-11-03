package com.MovieTicketBooking.App.Identity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "platform_admin")
@Getter
@Setter
@NoArgsConstructor
public class PlatformAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true,columnDefinition="citext")
    private String email;
    @Column(name="password_hash",nullable = false)
    private String passwordHash;
    @Column(name="created_at",nullable = false,updatable = false)
    private Instant createdAt;
}
