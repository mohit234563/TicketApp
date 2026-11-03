package com.MovieTicketBooking.App.Identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customers, UUID> {
    Optional<Customers> findByEmail(String email);
    Optional<Customers>findByPhone(String phone);
}
