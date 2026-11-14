package com.MovieTicketBooking.App.Catalog;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public final class VenueDtos {
    private VenueDtos() {}

    public record VenueRequest(@NotBlank String name, String city, String address) {}

    public record VenueResponse(UUID id, String name, String city, String address) {
        public static VenueResponse from(Venues v) {
            return new VenueResponse(v.getId(), v.getName(), v.getCity(), v.getAddress());
        }
    }
}