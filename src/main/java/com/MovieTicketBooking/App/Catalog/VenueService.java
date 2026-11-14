package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Common.CurrentTenant;
import com.MovieTicketBooking.App.Identity.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.MovieTicketBooking.App.Catalog.VenueDtos.*;
import java.util.List;
import java.util.UUID;

@Service
public class VenueService {
    private final TenantRepository tenants;
    private final VenueRepository repo;
    private final CurrentTenant currentTenant;
    public VenueService(TenantRepository tenants,VenueRepository repo,CurrentTenant currentTenant){
        this.tenants=tenants;
        this.repo=repo;
        this.currentTenant=currentTenant;
    }
    @Transactional(readOnly = true)
    public List<VenueResponse>list(){
        return repo.findByTenantId(currentTenant.id()).stream().map(VenueResponse::from).toList();
    }
    @Transactional
    public VenueResponse create(VenueRequest req){
        Venues v=new Venues();
        v.setTenant(tenants.getReferenceById(currentTenant.id()));
        apply(v,req);
        return VenueResponse.from(repo.save(v));
    }
    @Transactional
    public VenueResponse update(UUID id, VenueRequest req){
        Venues v=find(id);
        apply(v,req);
        return VenueResponse.from(v);
    }
    @Transactional
    public void delete(UUID id){
        repo.delete(find(id));
        repo.flush();
    }
    private Venues find(UUID id){
        return repo.findByIdAndTenantId(id,currentTenant.id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Venue not found"));
    }
    private void apply(Venues v, VenueRequest req){
        v.setName(req.name().trim());
        v.setCity(req.city());
        v.setAddress(req.address());
    }
}
