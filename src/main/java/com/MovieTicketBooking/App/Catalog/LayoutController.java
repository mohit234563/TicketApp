package com.MovieTicketBooking.App.Catalog;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.MovieTicketBooking.App.Catalog.LayoutDtos.*;
import java.util.*;
@RestController
@RequestMapping("/api/tenant/layout")
public class LayoutController {
    private final LayoutService service;
    public LayoutController(LayoutService service){
        this.service=service;
    }
    @GetMapping
    public List<layoutSummary>list(){
        return service.list();
    }
    @GetMapping("/{id}")
    public LayoutResponse get(@PathVariable  UUID id){
        return service.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LayoutResponse create(@Valid @RequestBody LayoutRequest req){
        return service.create(req);
    }
    @PutMapping("/{id}")
    public LayoutResponse update (@PathVariable  UUID id,@Valid @RequestBody LayoutRequest req){
        return service.update(id,req);
    }
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public LayoutResponse clone(@PathVariable UUID id,@RequestBody  String newName){
        return service.clone(id,newName);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable  UUID id){
        service.delete(id);
    }
}
