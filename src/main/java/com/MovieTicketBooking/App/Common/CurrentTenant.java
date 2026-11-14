package com.MovieTicketBooking.App.Common;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentTenant {

    public UUID id() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwt) {
            String t = jwt.getToken().getClaimAsString("tenantId");
            if (t != null) {
                return UUID.fromString(t);
            }
        }
        throw new AccessDeniedException("No tenant in token");
    }
}