package com.MovieTicketBooking.App.auth;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.MovieTicketBooking.App.auth.AuthDtos.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/admin/login")
    public TokenResponse adminLogin(@Valid @RequestBody AdminLoginRequest req) {
        return auth.loginAdmin(req);
    }

    @PostMapping("/staff/login")
    public TokenResponse staffLogin(@Valid @RequestBody StaffLoginRequest req) {
        return auth.loginStaff(req);
    }

    // Handy for testing: shows what the token says about you
    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("userId", jwt.getSubject(),
                "roles", jwt.getClaimAsStringList("roles"),
                "tenantId", String.valueOf(jwt.getClaimAsString("tenantId")));
    }
}