package com.MovieTicketBooking.App.Identity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final PlatformAdminRepository admins;
    private final TenantRepository tenants;
    private final PasswordEncoder encoder;
    private final StaffUserRepository staffUsers;

    public DevDataLoader(PlatformAdminRepository admins, PasswordEncoder encoder, TenantRepository tenants, StaffUserRepository staffUsers) {
        this.admins = admins;
        this.encoder = encoder;
        this.tenants = tenants;
        this.staffUsers = staffUsers;
    }

    @Override
    public void run(String... args) {
//        String email = "owner@example.com";
            Tenants t = new Tenants();
            t.setSlug("abc-bus");
            t.setName("ABC Bus Services");
            t.setBusinessType("bus");
            t.setEmail("abc@example.com");
            t.setStatus(TenantStatus.active);
            t = tenants.save(t);

            StaffUsers s = new StaffUsers();
            s.setTenant(t);
            s.setEmail("owner@abc.com");
            s.setFullname("ABC Owner");
            s.setRole(StaffRole.owner);
            s.setPasswordHash(encoder.encode("ChangeMe123!"));
            staffUsers.save(s);

        System.out.println(">>> Admins in DB: " + admins.count());

    }
}