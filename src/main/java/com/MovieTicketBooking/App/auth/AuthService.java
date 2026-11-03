package com.MovieTicketBooking.App.auth;



import com.MovieTicketBooking.App.Identity.*;
import com.MovieTicketBooking.App.Security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.MovieTicketBooking.App.auth.AuthDtos.*;

@Service
public class AuthService {

    private final PlatformAdminRepository admins;
    private final StaffUserRepository staff;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(PlatformAdminRepository admins, StaffUserRepository staff,
                       PasswordEncoder encoder, JwtService jwt) {
        this.admins = admins;
        this.staff = staff;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @Transactional(readOnly = true)
    public TokenResponse loginAdmin(AdminLoginRequest req) {
        PlatformAdmin admin = admins.findByEmail(req.email())
                .filter(a -> encoder.matches(req.password(), a.getPasswordHash()))
                .orElseThrow(AuthService::invalid);

        String token = jwt.issue(admin.getId(), List.of("PLATFORM_ADMIN"), null);
        return new TokenResponse(token, "Bearer", jwt.ttlSeconds());
    }

    @Transactional(readOnly = true)
    public TokenResponse loginStaff(StaffLoginRequest req) {
        StaffUsers user = staff.findByTenant_SlugAndEmail(req.tenantSlug(), req.email())
                .filter(StaffUsers::isActive)
                .filter(u -> u.getTenant().getStatus() == TenantStatus.active)
                .filter(u -> encoder.matches(req.password(), u.getPasswordHash()))
                .orElseThrow(AuthService::invalid);

        String role = "TENANT_" + user.getRole().name().toUpperCase();   // e.g. TENANT_BOX_OFFICE
        String token = jwt.issue(user.getId(), List.of(role), user.getTenant().getId());
        return new TokenResponse(token, "Bearer", jwt.ttlSeconds());
    }

    // Same message for unknown user, wrong password and inactive account,
    // so attackers can't tell which emails exist.
    private static ResponseStatusException invalid() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}