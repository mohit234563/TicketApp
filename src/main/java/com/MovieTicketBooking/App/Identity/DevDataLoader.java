package com.MovieTicketBooking.App.Identity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final PlatformAdminRepository admins;
    private final PasswordEncoder encoder;

    public DevDataLoader(PlatformAdminRepository admins, PasswordEncoder encoder) {
        this.admins = admins;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        String email = "owner@example.com";
        if (!admins.existsByEmail(email)) {
            PlatformAdmin a = new PlatformAdmin();
            a.setEmail(email);
            a.setPasswordHash(encoder.encode("ChangeMe123!"));
            admins.save(a);
        }
        System.out.println(">>> Admins in DB: " + admins.count());
    }
}