package com.MovieTicketBooking.App.Catalog;

import ch.qos.logback.core.Layout;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public final class LayoutDtos {
    private LayoutDtos(){}
    public record SeatInput(@NotBlank String label, String section, Integer rowNo, Integer colNo,
                            Map<String,Object> attributes){}

    public record LayoutRequest(@NotBlank String name, Map<String,Object>layoutJson, @NotEmpty @Valid List<SeatInput> seats){}
    public record CloneRequest(@NotBlank String name){}
    public record SeatView(UUID id, String label, String section, Integer rowNo, Integer colNo,Map<String,Object>attributes){
        static SeatView from(Seats s){
            return new SeatView(s.getId(),s.getLabel(),s.getSection(),s.getRowNo(),s.getColNo(),s.getAttributes());
        }
    }
    public record layoutSummary(UUID id ,String name,long seatCount){}
    public record LayoutResponse(UUID id ,String name,Map<String,Object>layoutJson,List<SeatView>seats){
        static LayoutResponse from(Layouts l,List<Seats>seats){
            Comparator<Integer>nullsLast=Comparator.nullsLast(Comparator.naturalOrder());
            List<SeatView>views=seats.stream()
                    .sorted(Comparator.comparing(Seats::getRowNo,nullsLast)
                     .thenComparing(Seats::getColNo,nullsLast))
                    .map(SeatView::from)
                    .toList();
            return new LayoutResponse(l.getId(),l.getName(),l.getLayoutJson(),views);
        }
    }
}
