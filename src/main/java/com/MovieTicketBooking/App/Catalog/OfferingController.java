package com.MovieTicketBooking.App.Catalog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.MovieTicketBooking.App.Catalog.OfferingDtos.*;
@RestController
@RequestMapping("/api/tenant/offering")
public class OfferingController {
    private final OfferingService service;
    public OfferingController(OfferingService service){
        this.service=service;
    }
    @GetMapping("/{id}")
    public OfferingResponse get(@PathVariable UUID id){
        return service.get(id);
    }
    @GetMapping
    public List<OfferingResponse>list(){
        return service.list();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferingResponse create(@RequestBody OfferingRequest req){
        return service.create(req);
    }
    @PutMapping("/{id}")
    public OfferingResponse update(@PathVariable UUID id,@RequestBody OfferingRequest req){
        return service.update(id,req);
    }
    @DeleteMapping("/{id}")
    public OfferingResponse deactivate(@PathVariable UUID id) {
        return service.setActive(id, false);
    }

    @PostMapping("/{id}/activate")
    public OfferingResponse activate(@PathVariable UUID id) {
        return service.setActive(id, true);
    }
}
