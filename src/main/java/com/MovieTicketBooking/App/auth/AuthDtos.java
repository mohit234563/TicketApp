package com.MovieTicketBooking.App.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthDtos {
    private AuthDtos() {}

    public record AdminLoginRequest(@NotBlank @Email String email, @NotBlank String password) {}

    public record StaffLoginRequest(@NotBlank String tenantSlug,
                                    @NotBlank @Email String email,
                                    @NotBlank String password) {}

    public record TokenResponse(String accessToken, String tokenType, long expiresInSeconds) {}
}