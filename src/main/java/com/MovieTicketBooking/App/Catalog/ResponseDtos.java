package com.MovieTicketBooking.App.Catalog;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public final class ResponseDtos {
    private ResponseDtos(){}
    public record ResourceRequest(@NotBlank  String name, UUID venueId, UUID layoutId,Integer capacity,Map<String,Object>attributes){}
    public record ResourceResponse(UUID id,String name,UUID venueId,UUID layoutId,Integer capacity,Map<String,Object>attributes){
        static ResourceResponse from(Resources r){
            return new ResourceResponse(r.getId(),r.getName(),
                    r.getVenue()==null?null:r.getVenue().getId(),
                    r.getLayout()==null?null:r.getLayout().getId(),
                    r.getCapacity(),r.getAttributes());
        }
    }
}
