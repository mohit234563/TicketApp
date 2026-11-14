package com.MovieTicketBooking.App.Catalog;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static  com.MovieTicketBooking.App.Catalog.VenueDtos.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenant/venue")
public class VenueController {
    private final VenueService service;
    public VenueController(VenueService service){
        this.service=service;
    }
    @GetMapping
    public List<VenueResponse> list(){
        return  service.list();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponse create(@Valid @RequestBody VenueRequest req){
        return service.create(req);
    }
    @PutMapping("/{id}")
    public VenueResponse update(@PathVariable UUID id,@Valid  @RequestBody VenueRequest req){
        return service.update(id,req);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UUID id){
        service.delete(id);
    }
}
