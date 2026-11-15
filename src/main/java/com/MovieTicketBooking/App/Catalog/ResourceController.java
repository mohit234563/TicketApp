package com.MovieTicketBooking.App.Catalog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.MovieTicketBooking.App.Catalog.ResponseDtos.*;
import java.util.*;
@RestController
@RequestMapping("/api/tenant/resource")
public class ResourceController {
    private final ResourceService service;
    public ResourceController(ResourceService service){
        this.service=service;
    }
    @GetMapping
    public List<ResourceResponse>list(){
        return service.list();
    }
    @GetMapping("/{id}")
    public ResourceResponse get(@PathVariable UUID id){
        return service.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceResponse create(@RequestBody ResourseRequest req){
        return service.create(req);
    }
    @PutMapping("/{id}")
    public ResourceResponse update(@PathVariable UUID id,@RequestBody ResourseRequest req){
        return service.update(id,req);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}
