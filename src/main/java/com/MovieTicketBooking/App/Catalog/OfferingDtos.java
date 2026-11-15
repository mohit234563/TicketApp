package com.MovieTicketBooking.App.Catalog;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public final class OfferingDtos {
    private OfferingDtos(){}
    public record StopInput(@NotBlank String stopName, @NotBlank String city, @NotNull @Positive Integer seq,@PositiveOrZero Integer minutesFromStart,
                            Boolean boarding, Boolean dropping){}
    public record OfferingRequest(@NotBlank String categoryCode,@NotBlank String title,
                                  String description,String posterUrl,
                                  Map<String,Object>attributes,
                                  @Valid List<StopInput>stops){}
    public record StopView(UUID id,String stopName,String city,Integer seq,Integer minutesFromStart,
                           Boolean boarding,Boolean dropping){
        static StopView from(OfferingStop s){
            return new StopView(s.getId(),s.getStopName(),s.getCity(),s.getSeq(),
                    s.getMinutesFromStart(),s.isBoarding(),s.isDropping());
        }
    }
    public record OfferingResponse(UUID id,String categoryCode,String title,String description,
                                   String posterUrl,Map<String,Object>attributes,boolean active,
                                   List<StopView>stops){}
}
