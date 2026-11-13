package com.MovieTicketBooking.App.Identity;

import com.MovieTicketBooking.App.Catalog.ServiceCategory;
import com.MovieTicketBooking.App.Catalog.ServiceCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final PlatformAdminRepository admins;
    private final TenantRepository tenants;
    private final StaffUserRepository staffUsers;
    private final ServiceCategoryRepository categories;
    private final PasswordEncoder encoder;

    public DevDataLoader(PlatformAdminRepository admins, TenantRepository tenants,
                         StaffUserRepository staffUsers, ServiceCategoryRepository categories,
                         PasswordEncoder encoder) {
        this.admins = admins;
        this.tenants = tenants;
        this.staffUsers = staffUsers;
        this.categories = categories;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        // Platform admin
        if (!admins.existsByEmail("owner@example.com")) {
            PlatformAdmin a = new PlatformAdmin();
            a.setEmail("owner@example.com");
            a.setPasswordHash(encoder.encode("ChangeMe123!"));
            admins.save(a);
        }

        // Tenant
        Tenants t = tenants.findBySlug("abc-bus").orElseGet(() -> {
            Tenants n = new Tenants();
            n.setSlug("abc-bus");
            n.setName("ABC Bus Services");
            n.setBusinessType("bus");
            n.setEmail("abc@example.com");
            n.setStatus(TenantStatus.active);
            return tenants.save(n);
        });

        // Tenant owner login
        if (staffUsers.findByTenantIdAndEmail(t.getId(), "owner@abc.com").isEmpty()) {
            StaffUsers s = new StaffUsers();
            s.setTenant(t);
            s.setEmail("owner@abc.com");
            s.setFullname("ABC Owner");
            s.setRole(StaffRole.owner);
            s.setActive(true);
            s.setPasswordHash(encoder.encode("ChangeMe123!"));
            staffUsers.save(s);
        }

        // Service categories
        Map<String, String> cats = Map.of("bus", "Bus", "movie", "Movies", "event", "Events");
        cats.forEach((code, name) -> {
            if (categories.findByCode(code).isEmpty()) {
                ServiceCategory c = new ServiceCategory();
                c.setCode(code);
                c.setName(name);
                categories.save(c);
            }
        });

        System.out.println(">>> Admins: " + admins.count() + ", tenants: " + tenants.count());
    }
}